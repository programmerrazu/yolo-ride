package com.intense.yolo.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.intense.yolo.R;
import com.intense.yolo.activity.MainActivity;
import com.intense.yolo.presenter.login.PasswordPresenter;
import com.intense.yolo.presenter.login.PasswordPresenterImpl;
import com.intense.yolo.view.login.PasswordView;

import static android.view.Gravity.RIGHT;

public class LoginWithPasswordActivity extends AppCompatActivity implements PasswordView {

    private ImageView ivBack;
    private EditText etPassword;
    private FABProgressCircle fabProgressCircle;
    private FrameLayout flLoginWithPassword;
    private PasswordPresenter passwordPresenter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_password);

        passwordPresenter = new PasswordPresenterImpl(this);
        flLoginWithPassword = (FrameLayout) findViewById(R.id.fl_login_with_password);
        etPassword = (EditText) findViewById(R.id.et_password);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fab_progress_circle);
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordPresenter.validatePassword(etPassword.getText().toString().trim());
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });

        Slide enterSlide = new Slide(RIGHT);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.ll_phone);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(RIGHT);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.ll_phone);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);

        etPassword.setCursorVisible(true);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordPresenter.onDestroy();
    }

    void nextActivity() {
        etPassword.setCursorVisible(false);
        flLoginWithPassword.setAlpha(0.4f);
        fabProgressCircle.show();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginWithPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    void back() {
        onBackPressed();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setPasswordError() {
        snackBarMsg("Password empty");
    }

    @Override
    public void navigateMain() {
        nextActivity();
    }

    @Override
    public void showAlert(String message) {
        snackBarMsg(message);
    }

    private void snackBarMsg(String message) {
        Snackbar snackbar = Snackbar.make(flLoginWithPassword, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        back();
    }
}