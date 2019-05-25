package com.intense.yolo.localdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.intense.yolo.entity.BookingInfo;

@Database(entities = BookingInfo.class, version = 1, exportSchema = false)
public abstract class BookingDB extends RoomDatabase {

    public abstract BookingDao bookingDao();
}