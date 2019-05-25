package com.intense.yolo.model;

import com.intense.yolo.entity.DBLocationInfo;

import java.util.List;

public interface DBLocationModel {

    interface OnDBLocationCheckListener {

        void onDBLocationSuccess(List<DBLocationInfo> infoList);

        void onDBLocationError();

        void onDBLocationFailure(String message);
    }

    void getDBLocation(OnDBLocationCheckListener listener);
}