package com.mvc.config;

import com.mvc.Entity.JsonResponse;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.model.AuthorizationManager;
import com.mvc.model.Hasher;
import com.mvc.model.UserValidator;
import com.mvc.model.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelConfig {

    @Bean
    public AuthorizationManager getAuthorizationManager()
    {
        DaoConfig daoConfig = new DaoConfig();
        return new AuthorizationManager(daoConfig.getUserDaoMysql(), new Hasher(), getUserValidator(), getJsonWrapper());
    }

    @Bean
    public Validator getUserValidator()
    {
        return new UserValidator();
    }

    @Bean
    public JsonResponseWrapper getJsonWrapper(){return new JsonResponse();}
}
