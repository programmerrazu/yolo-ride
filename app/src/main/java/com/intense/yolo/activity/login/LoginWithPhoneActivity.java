package com.intense.yolo.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.intense.yolo.R;
import com.intense.yolo.presenter.login.PhonePresenter;
import com.intense.yolo.presenter.login.PhonePresenterImpl;
import com.intense.yolo.view.login.PhoneView;

import static android.view.Gravity.LEFT;

public class LoginWithPhoneActivity extends AppCompatActivity implements PhoneView {

    private ImageView ivBack, ivFlag;
    private TextView tvMoving, tvPhoneCode;
    private LinearLayout llPhone;
    private EditText etPhoneNo;
    private FrameLayout flLoginWithPhone;
    private FABProgressCircle fabProgressCircle;
    // private ProgressDialog pbDialog;
    private PhonePresenter phonePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);

        // pbDialog = new ProgressDialog(this);
        phonePresenter = new PhonePresenterImpl(this);

        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivFlag = (ImageView) findViewById(R.id.iv_flag);
        tvMoving = (TextView) findViewById(R.id.tv_moving);
        tvPhoneCode = (TextView) findViewById(R.id.tv_phone_code);
        etPhoneNo = (EditText) findViewById(R.id.et_phone_no);
        llPhone = (LinearLayout) findViewById(R.id.ll_phone);
        flLoginWithPhone = (FrameLayout) findViewById(R.id.fl_login_with_phone);
        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fab_progress_circle);
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonePresenter.validatePhone(etPhoneNo.getText().toString().trim());
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReturnTransition();
            }
        });

        setupWindowAnimations();
    }

    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        ChangeBounds enterTransition = new ChangeBounds();
        enterTransition.setDuration(1000);
        enterTransition.setInterpolator(new DecelerateInterpolator(4));
        enterTransition.addListener(enterTransitionListener);
        getWindow().setSharedElementEnterTransition(enterTransition);

        ChangeBounds returnTransition = new ChangeBounds();
        returnTransition.setDuration(1000);
        returnTransition.addListener(returnTransitionListener);
        getWindow().setSharedElementReturnTransition(returnTransition);

        Slide exitSlide = new Slide(LEFT);
        exitSlide.setDuration(700);
        exitSlide.addListener(exitTransitionListener);
        exitSlide.addTarget(R.id.ll_phone);
        exitSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(exitSlide);

        Slide reenterSlide = new Slide(LEFT);
        reenterSlide.setDuration(700);
        reenterSlide.addListener(reenterTransitionListener);
        reenterSlide.setInterpolator(new DecelerateInterpolator(2));
        reenterSlide.addTarget(R.id.ll_phone);
        getWindow().setReenterTransition(reenterSlide);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phonePresenter.onDestroy();
    }

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhoneActivity.this, R.anim.slide_right);
            ivBack.startAnimation(animation);

            /* etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0); */
        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener returnTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

           /* etPhoneNo.setCursorVisible(false);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0); */

            etPhoneNo.setVisibility(View.GONE);
            etPhoneNo.setBackground(null);
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            ivFlag.setImageAlpha(0);
            tvPhoneCode.setAlpha(0);
            Animation animation = AnimationUtils.loadAnimation(LoginWithPhoneActivity.this, R.anim.slide_left);
            ivBack.startAnimation(animation);
        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener exitTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            flLoginWithPhone.setAlpha(1f);
            fabProgressCircle.hide();
            llPhone.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    Transition.TransitionListener reenterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {
            llPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));

            /* etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0); */
        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    };

    void nextPager() {
        etPhoneNo.setCursorVisible(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0);

        flLoginWithPhone.setAlpha(0.4f);
        fabProgressCircle.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginWithPhoneActivity.this, LoginWithPasswordActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginWithPhoneActivity.this);
                startActivity(intent, options.toBundle());
            }
        }, 1000);
    }

    void startReturnTransition() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        /* pbDialog.show();*/
    }

    @Override
    public void hideProgress() {
        /* if (pbDialog != null && pbDialog.isShowing()) {
            pbDialog.dismiss();
        } */
    }

    @Override
    public void setPhoneError() {
        snackBarMsg("Phone number empty");
    }

    @Override
    public void navigatePassword() {
        nextPager();
    }

    @Override
    public void showAlert(String message) {
        snackBarMsg("Invalid phone number");
    }

    private void snackBarMsg(String message) {
        Snackbar snackbar = Snackbar.make(flLoginWithPhone, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        startReturnTransition();
    }
}