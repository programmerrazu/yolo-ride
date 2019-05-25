package com.intense.yolo.view;

import com.intense.yolo.entity.DBLocationInfo;

import java.util.List;

public interface DBLocationView {

    void showProgress();

    void hideProgress();

    void setDBLocationError();

    void listDBLocation(List<DBLocationInfo> infoList);

    void showAlert(String message);
}