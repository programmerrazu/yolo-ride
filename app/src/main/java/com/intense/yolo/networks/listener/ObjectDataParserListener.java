package com.intense.yolo.networks.listener;

public interface ObjectDataParserListener {

    void onDataLoadSuccessfully(String riderInfo);

    void onDataLoadFailed(String riderInfo);
}