package com.azad.ListShare.models.responses;

import com.azad.ListShare.models.CustomList;
import com.azad.ListShare.models.Item;

import java.util.List;

public class CustomListResponse extends CustomList {

    private Long id;
    private Long userId;
    private List<Item> items;

    public CustomListResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
