package com.intense.yolo.localdb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.intense.yolo.entity.BookingInfo;

import java.util.List;

@Dao
public interface BookingDao {

    @Insert
    public long insertBooking(BookingInfo bookingInfo);

    @Query("SELECT * FROM booking_info ORDER BY booking_id DESC")
    public List<BookingInfo> getBookingInfo();
}