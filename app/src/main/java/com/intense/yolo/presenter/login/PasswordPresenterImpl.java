package com.intense.yolo.presenter.login;

import com.intense.yolo.model.login.PasswordModel;
import com.intense.yolo.model.login.PasswordModelImpl;
import com.intense.yolo.view.login.PasswordView;

public class PasswordPresenterImpl implements PasswordPresenter, PasswordModel.OnPasswordCheckListener {

    PasswordView passwordView;
    PasswordModel passwordModel;

    public PasswordPresenterImpl(PasswordView passwordView) {
        this.passwordView = passwordView;
        this.passwordModel = new PasswordModelImpl();
    }

    @Override
    public void validatePassword(String password) {
        if (passwordView != null) {
            passwordView.showProgress();
            passwordModel.onPasswordCheck(password, this);
        }
    }

    @Override
    public void onDestroy() {
        if (passwordView != null) {
            passwordView = null;
        }
    }

    @Override
    public void onPasswordError() {
        if (passwordView != null) {
            passwordView.hideProgress();
            passwordView.setPasswordError();
        }
    }

    @Override
    public void onPasswordSuccess() {
        if (passwordView != null) {
            passwordView.hideProgress();
            passwordView.navigateMain();
        }
    }

    @Override
    public void onPasswordFailure(String message) {
        if (passwordView != null) {
            passwordView.hideProgress();
            passwordView.showAlert(message);
        }
    }
}