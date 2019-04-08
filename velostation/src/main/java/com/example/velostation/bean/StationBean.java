package com.example.velostation.bean;

public class StationBean {

    private Integer number;
    private String contract_name;
    private String name;
    private String address;
    private PositionBean position;
    private Boolean banking;
    private Boolean bonus;
    private Integer bike_stands;
    private Integer available_bike_stands;
    private Integer available_bikes;
    private String status;
    private Integer last_update;

    public StationBean() {
        super();
    }

    public StationBean(Integer number, String contract_name, String name, String address, PositionBean position, Boolean banking, Boolean bonus, Integer bike_stands, Integer available_bike_stands, Integer available_bikes, String status, Integer last_update) {
        this.number = number;
        this.contract_name = contract_name;
        this.name = name;
        this.address = address;
        this.position = position;
        this.banking = banking;
        this.bonus = bonus;
        this.bike_stands = bike_stands;
        this.available_bike_stands = available_bike_stands;
        this.available_bikes = available_bikes;
        this.status = status;
        this.last_update = last_update;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContract_name() {
        return contract_name;
    }

    public void setContract_name(String contract_name) {
        this.contract_name = contract_name;
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

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public Boolean getBanking() {
        return banking;
    }

    public void setBanking(Boolean banking) {
        this.banking = banking;
    }

    public Boolean getBonus() {
        return bonus;
    }

    public void setBonus(Boolean bonus) {
        this.bonus = bonus;
    }

    public Integer getBike_stands() {
        return bike_stands;
    }

    public void setBike_stands(Integer bike_stands) {
        this.bike_stands = bike_stands;
    }

    public Integer getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(Integer available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public Integer getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(Integer available_bikes) {
        this.available_bikes = available_bikes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLast_update() {
        return last_update;
    }

    public void setLast_update(Integer last_update) {
        this.last_update = last_update;
    }
}
