package com.intense.yolo.model;

import com.intense.yolo.entity.DBLocationInfo;
import com.intense.yolo.networks.AppsApi;
import com.intense.yolo.networks.listener.StringDataParserListener;
import com.intense.yolo.networks.manager.data.DataManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBLocationModelImpl implements DBLocationModel, AppsApi {

    @Override
    public void getDBLocation(OnDBLocationCheckListener listener) {
        connectToServer(DB_LOCATION, listener);
    }

    private void connectToServer(String url, final OnDBLocationCheckListener listener) {
        DataManager.taskManager(url, new StringDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(String response) {
                locationParser(response, listener);
            }

            @Override
            public void onDataLoadFailed(String response) {
                listener.onDBLocationError();
            }
        });
    }

    private void locationParser(String response, OnDBLocationCheckListener listener) {
        List<DBLocationInfo> locationInfoList = new ArrayList<DBLocationInfo>();
        DBLocationInfo info = null;
        JSONObject responseObj = null;
        try {
            if (response != null) {
                responseObj = new JSONObject(response);
                JSONArray driverArray = responseObj.getJSONArray("locationlist");
                for (int i = 0; i < driverArray.length(); i++) {
                    info = new DBLocationInfo();
                    info.setName(driverArray.get(i).toString());
                    locationInfoList.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (locationInfoList.size() != 0) {
            listener.onDBLocationSuccess(locationInfoList);
        } else {
            listener.onDBLocationFailure("Location not found");
        }
    }
}