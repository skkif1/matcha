package com.mvc.DaoImplMysql;

import com.mvc.DAO.UserInformationDao;
import com.mvc.Entity.InformationBoundledUser;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserInformationDaoImpl implements UserInformationDao{

    private JdbcTemplate template;

    @Autowired
    public UserInformationDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Boolean saveUserInformation(InformationBoundledUser user) {
        String sql = "INSERT INTO user_information (user_id, age, country, state, aboutMe, sexPref) VALUES (?,?,?,?,?,?)";
        template.update(sql, user.getId(), user.getAge(), user.getCountry(), user.getState(), user.getAboutMe(), user.getSexPref());
        return true;
    }

    @Override
    public Boolean updateUserInformation(InformationBoundledUser user) {
        return null;
    }

    @Override
    public User getUserInformation()
    {
        return null;
    }
}
