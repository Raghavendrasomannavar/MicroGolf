package com.example.cs478.project4mtgame;

public class Hole {

    private int holeNumber;
    private int image;
    private int groupId;

    public Hole(int holeNumber, int image, int groupId) {
        this.holeNumber = holeNumber;
        this.image = image;
        this.groupId = groupId;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
