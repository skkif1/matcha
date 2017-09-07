package com.matcha.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler
{

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle400(Exception e)
    {
        return new ModelAndView("404");
    }
}
