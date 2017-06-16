package com.mvc.config;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;


@Component
public class ApplicationEventListener{

    public static final String CDN_SERVER_ADDRESS = "/nfs/2016/o/omotyliu/matchaUsersPhoto/";


    @EventListener
    public void handleRefresh(ContextRefreshedEvent event) {
        File userPhotoFolder = new File(CDN_SERVER_ADDRESS);

        if (!userPhotoFolder.exists())
        {
            userPhotoFolder.mkdir();
            System.out.println("CREATED");
        }
    }
}
