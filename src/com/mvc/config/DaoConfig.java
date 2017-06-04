package com.mvc.config;

import com.mvc.DAO.UserDao;
import com.mvc.DaoImpl.UserDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;


@Configuration
public class DaoConfig extends WebMvcConfigurerAdapter{

    @Bean
    public DataSource getMysqlDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/matcha");
        dataSource.setUsername("root");
        dataSource.setPassword("75g03f24");
        return dataSource;
    }


    @Bean(name = "getUserDaoMysql")
    public UserDao getUserDaoMysql()
    {
        return new UserDaoImpl(getMysqlDataSource());
    }
}
