package com.mvc.DaoImplMysql;

import com.mvc.DAO.UserDao;
import com.mvc.Entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl implements UserDao{

    private JdbcTemplate template;

    public UserDaoImpl() {
    }

    public UserDaoImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean saveUser(User user) {
       String sql = "INSERT INTO user (login, password, email, salt, last_name, first_name) VALUES (?,?,?,?,?,?)";
        int res = template.update(sql, user.getLogin(), user.getPassword(), user.getEmail(), user.getSalt(), user.getLastName(), user.getFirstName());
        return res > 0;
    }

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET login = ?, password = ?, email = ?, salt = ?, confirm = ? WHERE id = ?";
        int res = template.update(sql, user.getLogin(), user.getPassword(), user.getEmail(), user.getSalt(), user.getConfirm(), user.getId());
        return res > 0;
    }

    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT * FROM user WHERE login = ?";
        User user;

        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), new String[]{login});
        }catch (DataAccessException ex)
        {
            System.out.println(ex.getMessage());
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
            System.out.println("bd");
            System.out.println(ex.getMessage());
            return null;
        }
        return user;
    }

    @Override
    public Integer addEmailUpdateRequest(User user) {
        String sqlDelete = "DELETE FROM email_on_update WHERE user_id = ?";
        String sqlInsert = "INSERT INTO email_on_update (user_id, email_on_update) VALUES (?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();
        template.update(sqlDelete, user.getId());
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement st =  con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                st.setInt(1, user.getId());
                st.setString(2, user.getEmail());
                return st;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public Integer getEmailUpdateRequest(Integer id)
    {
        String sql = "SELECT user_id FROM email_on_update WHERE id = ?";

        Integer email = template.queryForObject(sql, new Object[]{id}, Integer.class);
        return email;
    }


    /*
    *
    *
    * */
}
