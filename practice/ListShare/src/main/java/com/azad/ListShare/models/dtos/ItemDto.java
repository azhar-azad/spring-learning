package com.azad.ListShare.models.dtos;

import com.azad.ListShare.models.CustomList;
import com.azad.ListShare.models.Item;

public class ItemDto extends Item {

    private Long id;
    private CustomList list;

    public ItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomList getList() {
        return list;
    }

    public void setList(CustomList list) {
        this.list = list;
    }
}
