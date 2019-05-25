package com.intense.yolo.entity;

public class VehicleInfo {

    private String id;
    private String name;
    private String image;
    private Integer passenger;
    private Integer baggage;
    private Integer luggage;
    private String vehicleType;
    private Float estimateFare;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPassenger() {
        return passenger;
    }

    public void setPassenger(Integer passenger) {
        this.passenger = passenger;
    }

    public Integer getBaggage() {
        return baggage;
    }

    public void setBaggage(Integer baggage) {
        this.baggage = baggage;
    }

    public Integer getLuggage() {
        return luggage;
    }

    public void setLuggage(Integer luggage) {
        this.luggage = luggage;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Float getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(Float estimateFare) {
        this.estimateFare = estimateFare;
    }
}