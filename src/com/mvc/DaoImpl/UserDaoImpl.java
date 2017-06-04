package com.mvc.DaoImpl;

import com.mvc.DAO.UserDao;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class UserDaoImpl implements UserDao{

    private JdbcTemplate template;

    public UserDaoImpl() {
    }

    public UserDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveUser(User user) {
       String sql = "INSERT INTO user (login, password, email, salt) VALUES (?,?,?,?)";
        int res = template.update(sql, user.getLogin(), user.getPassword(), user.getEmail(), user.getSalt());
        System.out.println("saved : " + res);
        return res < 0;
    }

    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        User user;

        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new String[]{login});
        }catch (DataAccessException ex)
        {
            return null;
        }
        return user;
    }


    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        User user;

        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new String[]{email});
        }catch (DataAccessException ex)
        {
            return null;
        }
        return user;
    }
}
