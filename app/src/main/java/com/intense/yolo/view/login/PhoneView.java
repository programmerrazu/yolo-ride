package com.intense.yolo.view.login;

public interface PhoneView {

    void showProgress();

    void hideProgress();

    void setPhoneError();

    void navigatePassword();

    void showAlert(String message);
}