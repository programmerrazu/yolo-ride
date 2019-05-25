package com.intense.yolo.model.login;

import android.text.TextUtils;
import android.util.Log;

import com.intense.yolo.networks.AppsApi;
import com.intense.yolo.networks.listener.StringDataParserListener;
import com.intense.yolo.networks.manager.data.DataManager;

public class PasswordModelImpl implements PasswordModel, AppsApi {


    @Override
    public void onPasswordCheck(String password, OnPasswordCheckListener listener) {
        if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
        } else {
            listener.onPasswordSuccess();
        }
    }

    private void connectToServer(String url, final OnPasswordCheckListener listener) {
        DataManager.taskManager(url, new StringDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(String response) {
                Log.d("TAG", response);
                listener.onPasswordSuccess();
            }

            @Override
            public void onDataLoadFailed(String response) {
                listener.onPasswordFailure("Invalid password");
            }
        });
    }
}