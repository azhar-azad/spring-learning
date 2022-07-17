package com.azad.bazaar.models.dtos;

import com.azad.bazaar.models.Item;
import com.azad.bazaar.models.Member;

public class ItemDto extends Item {

    private Long id;

    private Member addedBy;
    private String addedByName;

    public ItemDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Member addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedByName() {
        return addedByName;
    }

    public void setAddedByName(String addedByName) {
        this.addedByName = addedByName;
    }
}
