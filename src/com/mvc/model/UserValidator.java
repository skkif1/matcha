package com.mvc.model;

import com.mvc.Entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Validation Framework or Libraries are not allowd in this project among as ORM Frameworks
 */
@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class UserValidator implements Validator {

    private User user;
    private ArrayList<String> errors;
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    private static final String LOGIN_PATTERN = "^[a-z0-9_-]{6,32}$";
    private static final String PASSWORD_PATTERN = "^[a-z0-9_-]{6,15}$";

    public UserValidator() {
        System.out.println("Validator constracted");
    }

    public void setUser(User user) {
        this.errors = new ArrayList<>(10);
        this.user = user;
    }

    public ArrayList<String> getErrors() {
        return this.errors;
    }

    public Boolean validateUser(String validationLevel) {

        if(validationLevel.equals("signUp"))
            validateSignUp();
        if (validationLevel.equals("login"))
            validateLogin();
        return errors.size() == 0;
    }

    private void validateSignUp()
    {
        if (user == null)
            errors.add("empty user");

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (StringUtils.isEmpty(user.getPassword()) || !pattern.matcher(user.getPassword()).matches())
            errors.add("Invalid Password");

        pattern = Pattern.compile(LOGIN_PATTERN);
        if (StringUtils.isEmpty(user.getLogin()) || !pattern.matcher(user.getLogin()).matches())
            errors.add("Invalid Login");

        pattern = Pattern.compile(EMAIL_PATTERN);
        if (StringUtils.isEmpty(user.getEmail()) || !pattern.matcher(user.getEmail()).matches())
            errors.add("Invalid Email");
    }

    private void validateLogin()
    {
        if (user == null)
            errors.add("empty user");

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        if (StringUtils.isEmpty(user.getPassword()) || !pattern.matcher(user.getPassword()).matches())
            errors.add("Invalid Password");

        pattern = Pattern.compile(LOGIN_PATTERN);
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        if ((StringUtils.isEmpty(user.getLogin()) || !pattern.matcher(user.getLogin()).matches()))
            if (!emailPattern.matcher(user.getLogin()).matches())
                errors.add("Invalid Login or Email");
    }
}
