package com.azad.ListShare.models.requests;

import com.azad.ListShare.models.CustomList;
import com.azad.ListShare.models.Item;

import java.util.List;

public class CustomListRequest extends CustomList {

    private List<Item> items;

    public CustomListRequest() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
