package com.intense.yolo.model;

import android.os.Handler;
import android.text.TextUtils;

public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String userName, String password, final OnLoginFinishedListener listener) {
        if (TextUtils.isEmpty(userName)) {
            listener.onUserNameError();
        } else if (TextUtils.isEmpty(password)) {
            listener.onPasswordError();
        } else if (userName.equals("admin") && password.equals("123456")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onSuccess();
                }
            }, 3000);
        } else {
            listener.onFailure("Invalid credentials");
        }
    }
}