package com.azad.bazaar.models.responses;

import com.azad.bazaar.models.Item;
import com.azad.bazaar.models.Member;

public class ItemResponse extends Item {

    private Long id;

    private Member addedBy;
    private String addedByName;

    public ItemResponse() {
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
