package com.intense.yolo.networks.listener;

public interface StringDataParserListener {

    void onDataLoadSuccessfully(String response);

    void onDataLoadFailed(String response);
}