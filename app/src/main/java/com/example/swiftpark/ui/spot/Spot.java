package com.example.swiftpark.ui.spot;

public class Spot  {

    private String name;
    private String lot;


    public Spot(){
        // empty constructor
    }


    public Spot(String name, String lot){

        this.name = name;
        this.lot = lot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
