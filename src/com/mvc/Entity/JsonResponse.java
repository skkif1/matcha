package com.mvc.Entity;

public class JsonResponse extends JsonResponseWrapper{

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