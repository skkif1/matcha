package com.mvc.DAO;

import com.mvc.Entity.InformationBoundledUser;
import com.mvc.Entity.User;

public interface UserInformationDao {

    public Boolean saveUserInformation(InformationBoundledUser user);
    public Boolean updateUserInformation(InformationBoundledUser user);
    public User getUserInformation();
}
