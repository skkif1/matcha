package com.matcha.dao.mysqlImpl;


import com.matcha.dao.InformationDao;
import com.matcha.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class InformationDaoImpl implements InformationDao {

    private JdbcTemplate template;

    @Autowired
    public InformationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUserInfo(UserInformation info, Integer userId) {
        String sql = "INSERT INTO user_information (user_id, sex, age, country, state, aboutMe, sexPref, latitude, longitude) VALUES (?,?,?,?,?,?,?,?,?)";
        String updateSql = "UPDATE user_information SET sex = ?, age = ?, country = ?, state = ?, aboutMe = ?, sexPref = ?, latitude = ?, longitude = ? WHERE user_id = ?";
        if (getUserInfoByUserId(userId) == null) {
            template.update(sql, userId, info.getSex(), info.getAge(), info.getCountry(), info.getState(), info.getAboutMe(), info.getSexPref(), info.getLatitude(), info.getLangitude());
            saveIntrests(info.getInterests());
            saveIntrestList(info.getInterests(), userId);
        } else {
            template.update(updateSql, info.getSex(), info.getAge(), info.getCountry(), info.getState(), info.getAboutMe(), info.getSexPref(), info.getLatitude(), info.getLangitude(), userId);
            ArrayList<String> temp = new ArrayList<>(info.getInterests());
            temp.removeAll(selectUserInterestList(userId));
            saveIntrests(temp);
            saveIntrestList(info.getInterests(), userId);
        }
    }

    @Override
    public UserInformation getUserInfoByUserId(Integer userId) {
        String sql = "SELECT * FROM user_information WHERE user_id = ?";
        String avatarSql = "SELECT path FROM user_photo WHERE id = (SELECT photo_id FROM user_information WHERE user_id = ?)";
        UserInformation userInfo = null;
        try {
            userInfo = template.queryForObject(sql, new BeanPropertyRowMapper<>(UserInformation.class), new Integer[]{userId});
            userInfo.setInterests((ArrayList<String>) selectUserInterestList(userId));
            userInfo.setPhotos((ArrayList<String>) getUserPhoto(userId));
            userInfo.setAvatar(template.queryForObject(avatarSql, new Integer[]{userId}, String.class));
        } catch (DataAccessException ex) {
            System.out.println(this.getClass() + " " + userInfo);
            if (userInfo != null)
                return userInfo;
            return null;
        }
        System.out.println(this.getClass() + " " + userInfo);
        return userInfo;
    }

    @Override
    public void savePhoto(MultipartFile[] photos, Integer userId) throws IOException {
        File userDirectory = new File(CDN_SERVER_ADDRESS + userId);
        if (!userDirectory.exists())
            userDirectory.mkdirs();
        else
        {
            for (MultipartFile photo : photos) {
                Files.write(Paths.get(userDirectory.getAbsolutePath() + "/" + photo.getOriginalFilename()), photo.getBytes());
                savePhoto(CDN_WEB_ADDRESS + userId + "/" + photo.getOriginalFilename(), userId);
            }
        }

    }
    @Override
    public void savePhoto(String address, Integer id) {
        String sql = "INSERT INTO user_photo (path, user_id) VALUES (?,?)";
        template.update(sql, address, id);
    }


    @Override
    public void deletePhoto(String path, Integer userId) {
        String sql = "DELETE FROM user_photo WHERE path = ? AND user_id = ?";
        template.update(sql, path, userId);
        File photo = new File(CDN_SERVER_ADDRESS + path.substring(26));
        photo.delete();
    }

    @Override
    public void saveAvatar(String path, Integer userId) {
        String sql = "UPDATE user_information SET photo_id = (SELECT id FROM user_photo WHERE path = ?)WHERE user_id = ?";
        template.update(sql, path, userId);
    }

    @Override
    public String getPhotoById(Integer photoId) {
        String sql = "SELECT path FROm user_photo WHERE id = ?";
        return template.queryForObject(sql, new Integer[]{photoId}, String.class);
    }



    private void saveIntrests(List<String> interests) {
        String batchSql = "INSERT INTO interests (name) VALUES (?)";
        try {
            template.batchUpdate(batchSql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, interests.get(i));
                }

                @Override
                public int getBatchSize() {
                    return interests.size();
                }
            });
        } catch (DuplicateKeyException ex) {
            //    NOP
        }
    }


    @Override
    public Boolean likeUser(Integer userId, Integer authorId) {

        String like = "INSERT INTO `like` (author_id, user_id) VALUES (?,?)";
        String ifExist = "SELECT EXISTS(SELECT * FROM `like` WHERE author_id = ? AND user_id = ?)";

        if (template.queryForObject(ifExist, new Integer[]{authorId, userId}, Integer.class) == 0)
        {
            template.update(like, authorId,  userId);
            updateRate(userId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkIfUserLiked(Integer userId, Integer visitorId) {
        String sql = "SELECT COUNT(*) FROM `like` WHERE author_id = ? AND user_id = ?";
        Integer res = template.queryForObject(sql, new Integer[]{visitorId, userId}, Integer.class);
        return (res == 1);
    }

    @Override
    public void saveVisit(Integer visitorId, Integer userId) {
        String ifExist = "SELECT EXISTS(SELECT * FROM visits WHERE visitor_id = ? AND user_id = ? AND (SELECT count(*) " +
                "FROM visits HAVING (time + INTERVAL 1 DAY) > now()) > 0);";
        String sql = "INSERT INTO visits (visitor_id, user_id) VALUES (?,?)";
        if (template.queryForObject(ifExist, new Integer[]{visitorId, userId}, Integer.class) == 0)
        {
            template.update(sql, visitorId, userId);
        }
    }

    private void saveIntrestList(List<String> interestNames, Integer userId) {

        String delSql = "DELETE  FROM interests_list WHERE user_id = ?";
        String sql = "INSERT interests_list SET user_id = ? , interest_id = (SELECT interests.id FROM interests WHERE interests.name = ?)";

        template.update(delSql, userId);
        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, userId);
                ps.setString(2, interestNames.get(i));
            }

            @Override
            public int getBatchSize() {
                return interestNames.size();
            }
        });
    }


    private void updateRate(Integer id)
    {
        String sql = "UPDATE user_information SET rate = (SELECT COUNT(*) FROM `like` WHERE user_id = ?) WHERE user_id = ?";
        template.update(sql, id, id);
    }

    private List<String> selectUserInterestList(Integer userId) {
        List<String> res = new ArrayList<>(13);
        String sql = "SELECT name FROM interests WHERE id IN (SELECT interest_id FROM interests_list WHERE user_id = ?)";
        template.query(sql, new Integer[]{userId}, (ResultSet rs) ->
        {
            res.add(rs.getString(1));
        });
        return res;
    }

    private List<String> getUserPhoto(Integer userId)
    {
        ArrayList<String> res = new ArrayList<>(11);
        String sql = "SELECT * FROM USER_PHOTO WHERE user_id = ?";
        template.query(sql, new Integer[]{userId}, (ResultSet rs) ->
        {
            res.add(rs.getString("path"));
        });
        return res;
    }


}

