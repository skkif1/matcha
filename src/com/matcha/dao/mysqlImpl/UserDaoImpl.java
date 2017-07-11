package com.matcha.dao.mysqlImpl;

import com.matcha.dao.UserDao;
import com.matcha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
public class UserDaoImpl implements UserDao{

    private JdbcTemplate template;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Integer saveUser(User user)
    {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO user (password, email, salt, last_name, first_name) VALUES (?,?,?,?,?)";
        template.update((Connection conn)->
        {
               PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, user.getPassword());
               ps.setString(2, user.getEmail());
               ps.setString(3, user.getSalt());
               ps.setString(4, user.getLastName());
               ps.setString(5, user.getFirstName());
               return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE user SET  email = ?, password = ?, salt = ?, confirm = ?,salt = ?, " +
                "first_name = ?, last_name = ? where id = ?";
        template.update(sql, user.getEmail(), user.getPassword(), user.getSalt(), user.getConfirm(),
                user.getSalt(), user.getFirstName(), user.getLastName(), user.getId());
    }

    @Override
    public User getUserById(Integer id) {
        User user;
        String sql = "SELECT * FROM user WHERE id = ?";
        user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new Integer[]{id});
        return user;
    }

    @Override
    public User getUserByLogin(String login)
    {
        String sql = "SELECT * FROM user WHERE login = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new String[]{login});
    }

    @Override
    public User getUserByEmail(String email)
    {
        User user;
        String sql = "SELECT * FROM user WHERE email = ?";
        user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new String[]{email});
        return user;
    }
}
