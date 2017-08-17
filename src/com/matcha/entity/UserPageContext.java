package com.matcha.entity;

public class UserPageContext {

    private boolean permissionForSearch;


    public UserPageContext() {
    }

    public boolean isPermissionForSearch() {
        return permissionForSearch;
    }

    public void setPermissionForSearch(boolean permissionForSearch) {
        this.permissionForSearch = permissionForSearch;
    }
}
