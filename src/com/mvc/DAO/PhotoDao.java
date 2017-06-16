package com.mvc.DAO;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface PhotoDao {

    public Boolean savePhoto(MultipartFile [] photos, Integer userId) throws IOException;
    public List<String> getPhotosByUserId(Integer userId);

}
