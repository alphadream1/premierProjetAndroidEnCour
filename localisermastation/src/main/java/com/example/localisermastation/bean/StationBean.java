package com.example.localisermastation.bean;

public class StationBean {
    private int number;

    private int bike_stands;

    private String address;

    private int available_bikes;

    private String bonus;


    private String name;

    private String contract_name;

    private PositionBean position;

    private int available_bike_stands;

    private String banking;


    private String status;


    @Override
    public String toString() {
        return "StationBean{" +
                "number=" + number +
                ", bike_stands=" + bike_stands +
                ", address='" + address + '\'' +
                ", available_bikes=" + available_bikes +
                ", bonus='" + bonus + '\'' +
                ", name='" + name + '\'' +
                ", contract_name='" + contract_name + '\'' +
                ", position=" + position +
                ", available_bike_stands=" + available_bike_stands +
                ", banking='" + banking + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBike_stands() {
        return bike_stands;
    }

    public void setBike_stands(int bike_stands) {
        this.bike_stands = bike_stands;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(int available_bikes) {
        this.available_bikes = available_bikes;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public int getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(int available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public String getBanking() {
        return banking;
    }

    public void setBanking(String banking) {
        this.banking = banking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
