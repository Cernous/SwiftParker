package com.example.swiftpark.ui.parkingSpot;

public class ParkingSpot {
    private String spotName;
    private String status;

    public ParkingSpot(){

    }

    public ParkingSpot(String spotName, String status){
        this.spotName = spotName;
        this.status = status;
    }

    public String getSpotName(){
        return spotName;
    }

    public String getStatus(){
        return status;
    }


}
