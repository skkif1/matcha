package com.mvc.model;

import com.mvc.DAO.UserDao;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class AuthorizationManager {

    private UserDao userDao;
    private Hasher hasher;
    private Validator validator;
    private JsonResponseWrapper jsonResponse;


    public AuthorizationManager() {
    }

    @Autowired
    public AuthorizationManager(UserDao userDao, Hasher hasher, Validator validator, JsonResponseWrapper jsonResponse) {
        this.userDao = userDao;
        this.hasher = hasher;
        this.validator = validator;
        this.jsonResponse = jsonResponse;
    }

    public JsonResponseWrapper signUpUser(User user) {
        try {

            validator.setUser(user);
            if (!validator.validateUser("signUp"))
            {
                jsonResponse.setAction("error");
                jsonResponse.setData(validator.getErrors());
                return jsonResponse;
            }
            if (userDao.getUserByEmail(user.getEmail()) != null || userDao.getUserByLogin(user.getLogin()) != null)
            {
                jsonResponse.setAction("error");
                ArrayList<String> errors = new ArrayList<>();
                errors.add("user with such email or login allready exist");
                jsonResponse.setData(errors);
                return jsonResponse;
            }
            String saltAndPassword[] = hasher.hashSaltPassword(user.getPassword()).split("\\&");
            user.setSalt(saltAndPassword[0]);
            user.setPassword(saltAndPassword[1]);
            if (userDao.saveUser(user))
            {
                jsonResponse.setAction("confirm");
                return jsonResponse;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        jsonResponse.setAction("Bderror");
        return jsonResponse;
    }

    public JsonResponseWrapper login(User user)
    {
        try {
            validator.setUser(user);
            jsonResponse.setAction("bla");//mock
            if (!validator.validateUser("login")) {
                jsonResponse.setAction("error");
                jsonResponse.setData(validator.getErrors());
                return jsonResponse;
            }
            User selected = userDao.getUserByEmail(user.getLogin());
            if (selected == null) {
                selected = userDao.getUserByLogin(user.getLogin());
                if (selected == null) {
                    jsonResponse.setAction("error");
                    jsonResponse.setData(new ArrayList<String>(Arrays.asList(new String[]{"no such user!\n Tap signUp button to create acount."})));
                    return jsonResponse;
                }
            }
        if (hasher.compareValues(user.getPassword(), selected.getPassword(), selected.getSalt()))
        {
            jsonResponse.setAction("confirm");
        }else
        {
            jsonResponse.setAction("error");
            jsonResponse.setData("Invalid password! Tap forgot password to restore your password");
        }
        return jsonResponse;

        }catch (Exception ex)
        {
            ex.printStackTrace();
            jsonResponse.setAction("serverError");
            jsonResponse.setData(null);
            return jsonResponse;
        }
    }
}
