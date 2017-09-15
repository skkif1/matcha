package com.matcha.model;

import com.matcha.dao.InformationDao;
import com.matcha.dao.UserDao;
import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Component
public class AuthorizationManager {

    private UserDao userDao;
    private IHasher hasher;
    private EmailSender emailSender;
    private InformationDao infoDao;

    public AuthorizationManager() {
    }

    @Autowired
    public AuthorizationManager(UserDao userDao, IHasher hasher, EmailSender emailSender, InformationDao dao) {
        this.infoDao = dao;
        this.userDao = userDao;
        this.hasher = hasher;
        this.emailSender = emailSender;
    }

    public JsonResponseWrapper signUpUser(User user) throws InvalidKeySpecException, NoSuchAlgorithmException {
        HashMap<String, Object> mailModel = new HashMap<>();
        JsonResponseWrapper json = new JsonResponseWrapper();

        String[] saltAndPassword = hasher.getSaltAndPassword(user.getPassword());
        user.setSalt(saltAndPassword[0]);
        user.setPassword(saltAndPassword[1]);

        try {
            mailModel.put("salt", user.getSalt().substring(330));
            mailModel.put("email", user.getEmail());
            emailSender.send("confirmationEmail.ftl", "confirmation email on Matcha.com", user.getEmail(), mailModel);
            Integer userId = userDao.saveUser(user);
            user.setId(userId);
            json.setStatus("OK");
        } catch (DataAccessException ex) {
            if (ex instanceof DuplicateKeyException) {
                json.setStatus("Error");
                json.setData(new ArrayList<String>(Arrays.asList(new String[]{"user with such email allready exist"})));
                return json;
            }
            throw ex;
        } catch (MailException ex) {
            ex.printStackTrace();
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"invalid email"})));
            return json;
        }
        return json;
    }

    public Boolean confirmEmail(String email, String salt) {
        User user = userDao.getUserByEmail(email);
        if (user.getEmail().equals(email) && user.getSalt().substring(330).equals(salt)) {
            user.setConfirm(true);
            userDao.updateUser(user);
            return true;
        }
        return false;
    }

    public JsonResponseWrapper logInUser(User user, HttpSession session) throws InvalidKeySpecException, NoSuchAlgorithmException {

        User storedUser = checkIfUserExist(user);
        UserInformation info;
        JsonResponseWrapper json = new JsonResponseWrapper();
        String error;
        if (storedUser == null) {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"No such user check your email"})));
            return json;
        }
        if ((error = checkConfirmation(storedUser)) != null) {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{error})));
            return json;
        }
        if ((error = checkPassword(user, storedUser)) != null) {
            json.setStatus("Error");
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{error})));
            return json;
        }
        info = infoDao.getUserInfoByUserId(storedUser.getId());
        storedUser.setInformation(info);
        session.setAttribute("user", storedUser);
        json.setStatus("OK");
        return json;
    }

    public JsonResponseWrapper resetUserPassword(User user) {
        Map<String, Object> mailModelMap = new HashMap<>();
        JsonResponseWrapper json = new JsonResponseWrapper();
        try {
            User selected = userDao.getUserByEmail(user.getEmail());
            json.setData(new ArrayList<String>(Arrays.asList(new String[]{"We have sent an email to " + selected.getEmail() + " with further instruction"})));
            mailModelMap.put("salt", selected.getSalt().substring(330));
            mailModelMap.put("email", selected.getEmail());
            emailSender.send("restorePassword.ftl", "password reset on matcha.com", selected.getEmail(), mailModelMap);
            json.setStatus("OK");
        } catch (Exception ex) {
            json.setStatus("Error");
        }
        return json;
    }

    public Boolean changePasswordRequest(String email, String salt) {
        User selected = userDao.getUserByEmail(email);
        if (selected != null) {
            if (salt.equals(selected.getSalt().substring(330)))
                return true;
        }
        return false;
    }

    public JsonResponseWrapper changePassword(String password, HttpSession session) {
        String email;
        User user;
        JsonResponseWrapper json = new JsonResponseWrapper();

        if ((email = (String) session.getAttribute("email")) != null) {
            user = userDao.getUserByEmail(email);
            String[] saltPassword = hasher.getSaltAndPassword(password);
            user.setSalt(saltPassword[0]);
            user.setPassword(saltPassword[1]);
            userDao.updateUser(user);
            session.removeAttribute("email");
            json.setStatus("OK");
            return json;
        }
        json.setStatus("Error");
        return json;
    }

    private String checkConfirmation(User user) {
        if (user.getConfirm())
            return null;
        return "You need confirm your email first, check your mail";
    }

    private String checkPassword(User user, User storedUser) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (hasher.compareValues(user.getPassword(), storedUser.getPassword(), storedUser.getSalt()))
            return null;
        return "Wrong password";
    }

    private User checkIfUserExist(User user) {
        User storedUser;
        try {
            storedUser = userDao.getUserByEmail(user.getEmail());
        } catch (DataAccessException ex) {
            if (ex instanceof EmptyResultDataAccessException)
                return null;
            throw ex;
        }
        return storedUser;
    }
}

