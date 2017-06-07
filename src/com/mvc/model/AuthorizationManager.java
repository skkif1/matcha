package com.mvc.model;

import com.mvc.DAO.UserDao;
import com.mvc.Entity.JsonResponseWrapper;
import com.mvc.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthorizationManager {

    private UserDao userDao;
    private Hasher hasher;
    private Validator validator;
    private JsonResponseWrapper<Object> jsonResponse;
    private EmailSender emailSender;

    public AuthorizationManager() {
    }

    @Autowired
    public AuthorizationManager(UserDao userDao, Hasher hasher, Validator validator, JsonResponseWrapper<Object> jsonResponse, EmailSender emailSender) {
        this.userDao = userDao;
        this.hasher = hasher;
        this.validator = validator;
        this.jsonResponse = jsonResponse;
        this.emailSender = emailSender;
    }

    public JsonResponseWrapper<Object> signUpUser(User user) {
        HashMap<String, Object> mailModel = new HashMap<>();

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
            mailModel.put("salt", user.getSalt().substring(330));
            mailModel.put("email",user.getEmail());
            emailSender.send("confirmationEmail.ftl", "confirmation email on Matcha.com", user.getEmail(), mailModel);
            if (userDao.saveUser(user))
            {
                jsonResponse.setAction("confirm");
                return jsonResponse;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            jsonResponse.setAction("Bderror");
        }
        return jsonResponse;
    }

    public JsonResponseWrapper<Object> login(User user)
    {
        try {
            validator.setUser(user);
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

    public Boolean confirmEmail(String email, String salt) {
        User selected = userDao.getUserByEmail(email);
        if (selected == null || selected.getConfirm() != false || !selected.getSalt().substring(330).equals(salt))
            return false;
        selected.setConfirm(true);
        userDao.updateUser(selected);
        return true;
    }
    
    public JsonResponseWrapper<Object> resetUserPassword(User user) {

    Map<String, Object> map = new HashMap<>();
    map.put("model", "Hellosad");
        emailSender.send("template.ftl", "asd", "skkif1@gmail.com", map);
//        try {
//            User selected = userDao.getUserByEmail(user.getEmail());
//            if (selected == null) {
//                jsonResponse.setAction("error");
//                jsonResponse.setData("no user with such email\n tap sign Up button to create acount");
//                return jsonResponse;
//            }
//            jsonResponse.setAction("confirm");
//            jsonResponse.setData(new ArrayList<String>(Arrays.asList(new String[]{"restore link was send to your email"})));
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error " + ex.getMessage());
//            jsonResponse.setAction("server error");
//        }
        return jsonResponse;
    }
}
