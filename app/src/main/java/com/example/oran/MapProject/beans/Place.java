package com.example.oran.MapProject.beans;

public class Place {
    private long id;
    private String name;
    private String address;
    private double lnt , alt;
    private String picture;

    public Place() {

    }

    public Place(long id, String name, String address, double lnt, double alt, String picture) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.lnt = lnt;
        this.alt = alt;
        this.picture = picture;
    }

    public Place(String name, String address, double lnt, double alt, String picture) {
        this.name = name;
        this.address = address;
        this.lnt = lnt;
        this.alt = alt;
        this.picture = picture;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public double getLnt() {
        return lnt;
    }

    public void setLnt(double lnt) {
        this.lnt = lnt;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", lnt=" + lnt +
                ", alt=" + alt +
                ", picture='" + picture + '\'' +
                '}';
    }
}
