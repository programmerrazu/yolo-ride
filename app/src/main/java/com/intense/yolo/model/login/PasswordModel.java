package com.intense.yolo.model.login;

public interface PasswordModel {

    interface OnPasswordCheckListener {

        void onPasswordError();

        void onPasswordSuccess();

        void onPasswordFailure(String message);
    }

    void onPasswordCheck(String phone, OnPasswordCheckListener listener);
}