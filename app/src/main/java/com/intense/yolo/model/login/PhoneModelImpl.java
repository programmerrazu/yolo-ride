package com.intense.yolo.model.login;

import android.text.TextUtils;

public class PhoneModelImpl implements PhoneModel {

    @Override
    public void onPhoneCheck(String phone, OnPhoneCheckListener listener) {
        if (TextUtils.isEmpty(phone)) {
            listener.onPhoneError();
        } else {
            listener.onPhoneSuccess();
        }

        /*else if (phone.equals("01912345678")) {
            listener.onPhoneSuccess();
        } else {
            listener.onPhoneFailure("Invalid phone");
        }*/
    }
}