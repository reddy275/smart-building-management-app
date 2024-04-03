package com.example.building;

public class ReviewModel {
    private int reviewID;
    private int complaintID;
    private float professionalism;
    private float qualityOfWork;
    private float communication;
    private float punctuality;
    private String comments;

    public ReviewModel(int reviewID, int complaintID, float professionalism, float qualityOfWork, float communication, float punctuality, String comments) {
        this.reviewID = reviewID;
        this.complaintID = complaintID;
        this.professionalism = professionalism;
        this.qualityOfWork = qualityOfWork;
        this.communication = communication;
        this.punctuality = punctuality;
        this.comments = comments;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public void setProfessionalism(float professionalism) {
        this.professionalism = professionalism;
    }

    public void setQualityOfWork(float qualityOfWork) {
        this.qualityOfWork = qualityOfWork;
    }

    public void setCommunication(float communication) {
        this.communication = communication;
    }

    public void setPunctuality(float punctuality) {
        this.punctuality = punctuality;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getReviewID() {
        return reviewID;
    }

    public int getComplaintID() {
        return complaintID;
    }

    public float getProfessionalism() {
        return professionalism;
    }

    public float getQualityOfWork() {
        return qualityOfWork;
    }

    public float getCommunication() {
        return communication;
    }

    public float getPunctuality() {
        return punctuality;
    }

    public String getComments() {
        return comments;
    }
}

