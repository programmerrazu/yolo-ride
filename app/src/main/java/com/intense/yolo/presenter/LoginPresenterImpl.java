package com.intense.yolo.presenter;

import com.intense.yolo.model.LoginModel;
import com.intense.yolo.model.LoginModelImpl;
import com.intense.yolo.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener {

    LoginView loginView;
    LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredential(String userName, String password) {
        if (loginView != null) {
            loginView.showProgress();
            loginModel.login(userName, password, this);
        }
    }

    @Override
    public void onDestroy() {
        if (loginView != null) {
            loginView = null;
        }
    }

    @Override
    public void onUserNameError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setUserNameError();
        }
    }

    @Override
    public void onPasswordError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setPasswordError();
        }
    }

    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.navigateMain();
        }
    }

    @Override
    public void onFailure(String message) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showAlert(message);
        }
    }
}