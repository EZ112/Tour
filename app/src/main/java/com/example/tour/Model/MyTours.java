package com.example.tour.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class MyTours {
    private int myTourID;
    private String tourID;
    private String tourName;
    private String tourDate;
    private int tourImages;
    private String userID;

    public MyTours(int myTourID, String tourID, String tourName, String tourDate, int tourImages, String userID) {
        this.myTourID = myTourID;
        this.tourID = tourID;
        this.tourName = tourName;
        this.tourDate = tourDate;
        this.tourImages = tourImages;
        this.userID = userID;
    }

    public int getMyTourID() {
        return myTourID;
    }

    public void setMyTourID(int myTourID) {
        this.myTourID = myTourID;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDate() {
        return tourDate;
    }

    public void setTourDate(String tourDate) {
        this.tourDate = tourDate;
    }

    public int getTourImages() {
        return tourImages;
    }

    public void setTourImages(int tourImages) {
        this.tourImages = tourImages;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
