package com.matcha.dao;

import com.matcha.entity.UserInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InformationDao {

    final String CDN_SERVER_ADDRESS = System.getProperty("user.dir") + "/resources/";

    public void saveUserInfo(UserInformation info, Integer userId);
    public UserInformation getUserInfoByUserId(Integer userId);
    public void savePhoto(MultipartFile[] photos, Integer userId) throws IOException;
    public Integer countPhoto(Integer UserId);
    public Integer countIntrests();
}
