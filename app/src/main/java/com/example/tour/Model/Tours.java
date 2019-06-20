package com.example.tour.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class Tours {
    private String tourID;
    private String tourName;
    private String tourDate;
    private long tourPrice;
    private int tourImages;

    public Tours(String tourID, String tourName, String tourDate, long tourPrice, int tourImages) {
        this.tourID = tourID;
        this.tourName = tourName;
        this.tourDate = tourDate;
        this.tourPrice = tourPrice;
        this.tourImages = tourImages;
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

    public long getTourPrice() {
        return tourPrice;
    }

    public void setTourPrice(long tourPrice) {
        this.tourPrice = tourPrice;
    }

    public int getTourImages() {
        return tourImages;
    }

    public void setTourImages(int tourImages) {
        this.tourImages = tourImages;
    }
}
