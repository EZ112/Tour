package com.example.tour.Model;

public class TourDest {
    private int tourDestID;
    private String tourDest;
    private String tourID;

    public TourDest(int tourDestID, String tourDest, String tourID) {
        this.tourDestID = tourDestID;
        this.tourDest = tourDest;
        this.tourID = tourID;
    }

    public int getTourDestID() {
        return tourDestID;
    }

    public void setTourDestID(int tourDestID) {
        this.tourDestID = tourDestID;
    }

    public String getTourDest() {
        return tourDest;
    }

    public void setTourDest(String tourDest) {
        this.tourDest = tourDest;
    }

    public String getTourID() {
        return tourID;
    }

    public void setTourID(String tourID) {
        this.tourID = tourID;
    }
}
