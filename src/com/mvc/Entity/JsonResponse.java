package com.mvc.Entity;

import org.springframework.stereotype.Component;

@Component
public class JsonResponse extends JsonResponseWrapper{

    public JsonResponse()
    {
        System.out.println("from controller " + super.action);
        System.out.println("JsonResponse.constructed");
    }

    @Override
    public void setAction(String action) {
        super.action = action;
    }

    @Override
    public void setData(Object data) {
        super.data = data;
    }

    @Override
    public String getAction() {
        return super.action;
    }

    @Override
    public Object getData() {
        return super.data;
    }

}