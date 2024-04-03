package com.example.building;

public class AnnouncementModel {
    private long announcementID;
    private String type;
    private String description;

    public AnnouncementModel() {
        // Default constructor
    }

    public AnnouncementModel(long announcementID, String type, String description) {
        this.announcementID = announcementID;
        this.type = type;
        this.description = description;
    }

    public long getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(long announcementID) {
        this.announcementID = announcementID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

