package com.azad.school.models;

import javax.validation.constraints.NotNull;

public class Class {

    @NotNull(message = "Classroom identifier cannot be empty")
    private String classRoom;

    private int floor;

    public Class() {
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
