package com.matcha.dao.mysqlImpl;


import com.matcha.dao.InformationDao;
import com.matcha.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
public class InformationDaoImpl implements InformationDao{

    private JdbcTemplate template;

    @Autowired
    public InformationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void saveUserInfo(UserInformation info, Integer userId) {
        String sql = "INSERT INTO user_information (user_id, sex, age, country, state, aboutMe, sexPref) VALUES (?,?,?,?,?,?,?)";
        String updateSql = "UPDATE user_information SET sex = ?, age = ?, country = ?, state = ?, aboutMe = ?, sexPref = ? WHERE user_id = ?";
        if (getUserInfoByUserId(userId) == null)
        {
            template.update(sql, userId, info.getSex(), info.getAge(), info.getCountry(), info.getState(), info.getAboutMe(), info.getSexPref());
            saveIntrests(info.getInterests());
            saveIntrestList(info.getInterests(), userId);
        }else
        {
            template.update(updateSql, info.getSex(), info.getAge(), info.getCountry(), info.getState(), info.getAboutMe(), info.getSexPref(), userId);
            info.getInterests().removeAll(selectUserInterestList(userId));
            saveIntrestList(info.getInterests(), userId);
        }
    }

    @Override
    public UserInformation getUserInfoByUserId(Integer userId) {
        String sql = "SELECT * FROM user_information WHERE user_id = ?";
        UserInformation userInfo;
        try {
            userInfo = template.queryForObject(sql, new BeanPropertyRowMapper<>(UserInformation.class), new Integer[]{userId});
            userInfo.setInterests((ArrayList<String>) selectUserInterestList(userId));
        } catch (DataAccessException ex) {
            return null;
        }
        return userInfo;
    }

    @Override
    public void savePhoto(MultipartFile[] photos, Integer userId) throws IOException {
        int photoName = 0;

        File userDirectory = new File(CDN_SERVER_ADDRESS + userId);
        System.out.println(userDirectory.getAbsolutePath());
        if(!userDirectory.exists())
            System.out.println(userDirectory.mkdirs());
        for (MultipartFile photo: photos)
        {
            Files.write(Paths.get(userDirectory.getAbsolutePath() + "/" + photoName++ + "." + photo.getContentType().split("\\/")[1]), photo.getBytes());
        }
    }

    @Override
    public Integer countPhoto(Integer UserId) {
        return null;
    }

    @Override
    public Integer countIntrests() {
        return null;
    }

    private void saveIntrests(List<String> interests) {
        String batchSql = "INSERT INTO interests (name) VALUES (?)";

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
    }

    private void saveIntrestList(List<String> interestNames, Integer userId) {

        String sql = "INSERT interests_list SET user_id = ? , interest_id = (SELECT interests.id FROM interests WHERE interests.name = ?)";

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

    private List<String> selectUserInterestList(Integer userId)
    {
        List<String> res = new ArrayList<>(13);
        String sql = "SELECT name FROM interests WHERE id IN (SELECT interest_id FROM interests_list WHERE user_id = ?)";
        template.query(sql, new Integer[]{userId}, (ResultSet rs) ->
        {
            res.add(rs.getString(1));
        });
        return res;
    }


}

