package com.azad.ListShare.models;

public class ShareAccess { // List access sharing to users

    private Long listId; // List in context
    private Long userId; // User that will get the access
    private Long accessId; // Access that is given to User

    public ShareAccess() {
    }

    public Long getListId() {
        return listId;
    }

    public void setListId(Long listId) {
        this.listId = listId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccessId() {
        return accessId;
    }

    public void setAccessId(Long accessId) {
        this.accessId = accessId;
    }
}
