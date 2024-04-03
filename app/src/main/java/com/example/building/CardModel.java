package com.example.building;

public class CardModel {

    private String card_name;
    private int card_image;

    private boolean isSelected = false;

    // Constructor
    public CardModel(String card_name, int card_image) {
        this.card_name = card_name;
        this.card_image = card_image;
    }

    // Getter and Setter
    public String getcard_name() {
        return card_name;
    }

    public void setcard_name(String card_name) {
        this.card_name = card_name;
    }

    public int getcard_image() {
        return card_image;
    }

    public void setcard_image(int card_image) {
        this.card_image = card_image;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
