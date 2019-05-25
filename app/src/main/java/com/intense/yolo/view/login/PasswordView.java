package com.intense.yolo.view.login;

public interface PasswordView {

    void showProgress();

    void hideProgress();

    void setPasswordError();

    void navigateMain();

    void showAlert(String message);
}