package com.example.building;

public class ResidentCardsModel {

    private String name;
    private String mobile;
    private int blockID;

    // Constructor
    public ResidentCardsModel(String name, String mobile, int blockID) {
        this.name = name;
        this.mobile = mobile;
        this.blockID = blockID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getBlockID() {
        return blockID;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    // Getter and Setter

}
