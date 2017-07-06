package com.matcha.model;

import com.matcha.dao.UserDao;
import com.matcha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/*
* need add of Hasher component
*
* add validation component or JSR 303
*
* JsonResponseWrapper need to br
*
* */

@Component
public class AuthorizationManager {

    private JsonResponseWrapper json; ///???????
    private UserDao userDao;
    private IHasher hasher;
    private EmailSender emailSender;

    public AuthorizationManager() {
    }

    @Autowired
    public AuthorizationManager(JsonResponseWrapper json, UserDao userDao, IHasher hasher, EmailSender emailSender) {
        this.json = json;
        this.userDao = userDao;
        this.hasher = hasher;
        this.emailSender = emailSender;
    }

    public JsonResponseWrapper signUpUser(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        HashMap<String, Object> mailModel = new HashMap<>();

        String[] saltAndPassword = hasher.getSaltAndPassword(user.getPassword());
        user.setSalt(saltAndPassword[0]);
        user.setPassword(saltAndPassword[1]);
        mailModel.put("salt", user.getSalt().substring(330));
        mailModel.put("email", user.getEmail());
        emailSender.send("confirmationEmail.ftl","confirmation email on Matcha.com", user.getEmail(), mailModel);
        Integer userId = userDao.saveUser(user);
        user.setId(userId);
        json.setStatus("OK");
        json.setData(user);
        return json;
    }


    public Boolean confirmEmail(String email, String salt)
    {
        User user = userDao.getUserByEmail(email);
        if(user.getEmail().equals(email) && user.getSalt().substring(330).equals(salt))
        {
            user.setConfirm(true);
            userDao.updateUser(user);
            return true;
        }
        return false;
    }

    public JsonResponseWrapper logInUser(User user, HttpSession session) throws InvalidKeySpecException, NoSuchAlgorithmException {

        User storedUser = checkIfUserExist(user);
        String error;
        if (storedUser == null)
        {
            System.out.println("asdasd");
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"No such user< check your login or email"})));
            return json;
        }
        if ((error = checkConfirmation(storedUser)) != null)
        {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{error})));
            return json;
        }
        if ((error = checkPassword(user, storedUser)) != null)
        {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{error})));
            return json;
        }
        session.setAttribute("user", storedUser);
        json.setStatus("OK");
        return json;
    }

    private String checkConfirmation(User user)
    {
        if (user.getConfirm())
            return null;
        return "You need confirm your email first, check your mail";
    }

    private String checkPassword(User user, User storedUser) throws InvalidKeySpecException, NoSuchAlgorithmException
    {
        if (hasher.compareValues(user.getPassword(), storedUser.getPassword(), storedUser.getSalt()))
            return null;
        return "Wrong password";
    }

    private User checkIfUserExist(User user)
    {
        User storedUser;
        storedUser = userDao.getUserByLogin(user.getLogin());
        if (storedUser == null)
            storedUser = userDao.getUserByEmail(user.getEmail());
        return storedUser;
    }
}
