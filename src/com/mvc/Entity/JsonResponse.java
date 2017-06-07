package com.mvc.Entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
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

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}