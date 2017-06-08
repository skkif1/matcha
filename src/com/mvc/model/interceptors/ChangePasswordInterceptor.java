package com.mvc.model.interceptors;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ChangePasswordInterceptor  {

    @Pointcut("execution(* com.mvc.controller.AuthorizationController.getAuthorization(..))")
    public void all(){};

    @Before("all()")
    public void sou()
    {
        System.out.println("before");
    }
}
