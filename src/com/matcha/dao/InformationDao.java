package com.matcha.dao;

import com.matcha.entity.Notification;
import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface InformationDao {

    final String CDN_SERVER_ADDRESS = "/nfs/2016/o/omotyliu/Library/Containers/MAMP/apache2/htdocs/cdn/";
    final String CDN_WEB_ADDRESS = "http://localhost:8081/cdn/";

    public void saveUserInfo(UserInformation info, Integer userId);
    public UserInformation getUserInfoByUserId(Integer userId);
    public void savePhoto(MultipartFile[] photos, Integer userId) throws IOException;
    public void savePhoto(String address, Integer id);
    public void deletePhoto(String path, Integer userId);
    public String getPhotoById(Integer photoId);
    public void saveAvatar(String path, Integer userId);
    public Boolean likeUser(Integer userId, Integer authorId);
    public List<User> getLikeAuthors(Integer userId);
    public void removeLike(Integer authorId, Integer userId);
    public Boolean saveVisit(Integer visitorId, Integer userId);
    public List<User> getUserVisitors(Integer userId);
    public void addUserToBlackList(Integer authorId, Integer userId);
    public Boolean checkIfUserLiked(Integer userId, Integer visitorId);
    public Boolean checkIfMatchedWith(Integer thisUserId, Integer userId);
    public List<User> getUserConnections(Integer userId);
    public void requisterMathedConnection(Integer thisUserId, Integer userId);
}
