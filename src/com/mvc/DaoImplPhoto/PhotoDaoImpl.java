package com.mvc.DaoImplPhoto;

import com.mvc.DAO.PhotoDao;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
public class PhotoDaoImpl implements PhotoDao{

    private final String CDN_SERVER_ADDRESS = "/nfs/2016/o/omotyliu/matchaUsersPhoto/";

    @Override
    public Boolean savePhoto(MultipartFile [] photos, Integer userId) throws IOException {

        int photoName = 0;

        File userDirectory = new File(CDN_SERVER_ADDRESS + userId);
        if(!userDirectory.exists())
            userDirectory.mkdir();
        for (MultipartFile photo: photos)
        {
            Files.write(Paths.get(userDirectory.getAbsolutePath() + "/" + photoName++ + "." + photo.getContentType().split("\\/")[1]), photo.getBytes());
        }
        return true;
    }

    @Override
    public List<String> getPhotosByUserId(Integer userId) {
        return null;
    }

}
