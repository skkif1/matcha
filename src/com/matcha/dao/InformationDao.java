package com.matcha.dao;

import com.matcha.entity.Notification;
import com.matcha.entity.SearchRequest;
import com.matcha.entity.User;
import com.matcha.entity.UserInformation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface InformationDao {

    final String CDN_SERVER_ADDRESS = "/nfs/2016/o/omotyliu/Library/Containers/MAMP/apache2/htdocs/cdn/";
    final String CDN_WEB_ADDRESS = "http://localhost:8081/cdn/";

    public List<User> searchUsersWith(String sex, String sexPref, Integer minAge, Integer maxAge, Integer minRate);
    public void saveUserInfo(UserInformation info, Integer userId);
    public UserInformation getUserInfoByUserId(Integer userId);
    public void savePhoto(MultipartFile[] photos, Integer userId) throws IOException;
    public void savePhoto(String address, Integer id);
    public void deletePhoto(String path, Integer userId);
    public String getPhotoById(Integer photoId);
    public void saveAvatar(String path, Integer userId);

    public void incrementRate(Integer authorId, Integer userId);

    public void likeUser(Integer userId, Integer authorId);
    public List<User> getLikeAuthors(Integer userId);
    public void removeLike(Integer authorId, Integer userId);
    public Boolean checkIfUserLiked(Integer userId, Integer visitorId);
    public void readNewLike(Integer userId);

    public Boolean saveVisit(Integer visitorId, Integer userId);
    public Boolean checkIfUserVisit(Integer visitorId, Integer userId);
    public List<User> getUserVisitors(Integer userId);
    public List<User> getUserVisits(Integer userId);
    public void readNewVisits(Integer userId);

    public Boolean checkIfMatchedWith(Integer thisUserId, Integer userId);
    public List<User> getUserConnections(Integer userId);
    public void requisterMathedConnection(Integer thisUserId, Integer userId);
    public void removeConnection(Integer user1Id, Integer user2Id);
    public void readNewConnections(Integer userId);

    public void setLastVisitTime(Integer userId, Boolean state);
    public void incrementRate(Integer id);

    public void addUserToBlackList(Integer authorId, Integer userId);
    public Boolean ifUserInBlackList(Integer listHolderId, Integer userId);

    public Integer getNewEventsNumber(Integer userId);
    public void getNewEventsNumber(Map<String, Integer> eventsHolder, Integer userId);
}

