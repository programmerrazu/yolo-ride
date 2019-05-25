package com.intense.yolo.presenter;

public interface LoginPresenter {

    void validateCredential(String userName, String password);

    void onDestroy();
}