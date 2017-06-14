package com.mvc.DaoImplMysql;

import com.mvc.DAO.UserInformationDao;
import com.mvc.Entity.User;
import com.mvc.Entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Generated;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserInformationDaoImpl implements UserInformationDao {

    private JdbcTemplate template;
    private NamedParameterJdbcTemplate namedTemplate;

    @Autowired
    public UserInformationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
        this.namedTemplate = new NamedParameterJdbcTemplate(template);
    }

    @Override
    public Boolean saveUserInformation(User user) {

        UserInformation info = user.getInformation();

        String sql = "INSERT INTO user_information (user_id, age, country, state, aboutMe, sexPref) VALUES (?,?,?,?,?,?)";
        template.update(sql, user.getId(), info.getAge(), info.getCountry(), info.getState(), info.getAboutMe(), info.getSexPref());
        saveIntrests(info.getInterests());
        ArrayList<Integer> interestsId = (ArrayList<Integer>) getInterestsByName(info.getInterests());
        saveIntrestList( interestsId, user.getId());
        return true;
    }

    @Override
    public Boolean updateUserInformation(User user) {
        return null;
    }

    @Override
    public User getUserInformation() {
        return null;
    }

    private List<Integer> getInterestsByName(List<String> interests) {
        String sql = "SELECT id FROM interests WHERE name IN (:name)";

        Map<String, List> params = new HashMap<>();
        params.put("name", interests);

        List<Integer> rs = namedTemplate.queryForList(sql, params, Integer.class);
        return rs;
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

    private void saveIntrestList(List<Integer> interestId, Integer userId) {
        String sql = "INSERT INTO interests_list (user_id, interest_id) VALUES(?, ?)";

        template.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, userId);
                ps.setInt(2, interestId.get(i));
            }

            @Override
            public int getBatchSize() {
                return interestId.size();
            }
        });
    }

    private List<String> getInterestsById(List<Integer> interestsId)
    {
        String sql = "SELECT name FROM interests WHERE id IN (:id)";

        Map<String, List> params = new HashMap<>();
        params.put("id", interestsId);

        List<String> rs = namedTemplate.queryForList(sql,params, String.class);
        return rs;
    }
}
