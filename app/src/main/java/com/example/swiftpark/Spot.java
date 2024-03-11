package com.example.swiftpark;

public class Spot {
    private int id;
    private String name;
    private String address;
    private String available;


    public Spot(String name, String address){
        this.name = name;
        this.address = address;
    }

    public Spot(int id, String name, String address, String available){
        this.id = id;
        this.name = name;
        this.address = address;
        this.available = available;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }


}
