package com.example.swiftpark;

public class Spot {
    private int id;
    private String name;
    private String available;
    private int check;

    public Spot(int id, String name, String available, int check){
        this.id = id;
        this.name =name;
        this.available = available;
        this.check = check;
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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public int getCheck() {
        return check;
    }

    public void setCheck(int check) {
        this.check = check;
    }
}
