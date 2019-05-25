package com.intense.yolo.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "booking_info")
public class BookingInfo {

    @ColumnInfo(name = "booking_id")
    @PrimaryKey(autoGenerate = true)
    private int bookingId;

    @ColumnInfo(name = "pickup_place")
    private String pickupPlace;

    @ColumnInfo(name = "dest_place")
    private String destPlace;

    @ColumnInfo(name = "min_price")
    private String minPrice;

    @ColumnInfo(name = "max_price")
    private String maxPrice;

    @ColumnInfo(name = "estimate_fare")
    private String estimateFare;

    @ColumnInfo(name = "distance")
    private String distance;

    @ColumnInfo(name = "duration")
    private String duration;

    @ColumnInfo(name = "vehicle_name")
    private String vehicleName;

    @ColumnInfo(name = "vehicle_type")
    private String vehicleType;

    @ColumnInfo(name = "vehicle_image")
    private String vehicleImage;

    @ColumnInfo(name = "journey_date")
    private String journeyDate;

    @ColumnInfo(name = "journey_time")
    private String journeyTime;

    @ColumnInfo(name = "insert_date")
    private String insertDate;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getPickupPlace() {
        return pickupPlace;
    }

    public void setPickupPlace(String pickupPlace) {
        this.pickupPlace = pickupPlace;
    }

    public String getDestPlace() {
        return destPlace;
    }

    public void setDestPlace(String destPlace) {
        this.destPlace = destPlace;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getEstimateFare() {
        return estimateFare;
    }

    public void setEstimateFare(String estimateFare) {
        this.estimateFare = estimateFare;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public void setVehicleImage(String vehicleImage) {
        this.vehicleImage = vehicleImage;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }
}