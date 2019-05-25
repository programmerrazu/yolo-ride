package com.intense.yolo.model;

public interface LoginModel {

    interface OnLoginFinishedListener {

        void onUserNameError();

        void onPasswordError();

        void onSuccess();

        void onFailure(String message);
    }

    void login(String userName, String password, OnLoginFinishedListener listener);
}