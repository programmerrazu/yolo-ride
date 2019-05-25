package com.intense.yolo.activity.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.intense.yolo.R;
import com.intense.yolo.activity.registration.RegistrationActivity;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivLogo, ivFlag;
    private TextView tvMoving, tvPhoneNo, tvCode;
    private LinearLayout llPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivLogo = (ImageView) findViewById(R.id.iv_logo);
        ivFlag = (ImageView) findViewById(R.id.iv_flag);
        tvMoving = (TextView) findViewById(R.id.tv_moving);
        tvCode = (TextView) findViewById(R.id.tv_phone_code);
        tvPhoneNo = (TextView) findViewById(R.id.tv_phone_no);
        llPhone = (LinearLayout) findViewById(R.id.ll_phone);

        setupWindowAnimations();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        ivLogo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (0.65 * height)));
    }

    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        ChangeBounds exitTransition = new ChangeBounds();
        exitTransition.setDuration(1000);
        exitTransition.addListener(exitListener);
        getWindow().setSharedElementExitTransition(exitTransition);

        ChangeBounds reenterTransition = new ChangeBounds();
        reenterTransition.setDuration(1000);
        reenterTransition.addListener(reenterListener);
        reenterTransition.setInterpolator(new DecelerateInterpolator(4));
        getWindow().setSharedElementReenterTransition(reenterTransition);
    }

    Transition.TransitionListener exitListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

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

    Transition.TransitionListener reenterListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(tvMoving, "alpha", 0f, 1f));
            animatorSet.setDuration(800);
            animatorSet.start();
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
            tvMoving.setAlpha(1);
        }
    };

    public void onLoginWithPhone(View view) {
        startTransition();
    }

    void startTransition() {
        Intent intent = new Intent(LoginActivity.this, LoginWithPhoneActivity.class);
        Pair<View, String> p2 = Pair.create((View) ivFlag, getString(R.string.transition_iv_flag));
        Pair<View, String> p3 = Pair.create((View) tvCode, getString(R.string.transition_tv_code));
        Pair<View, String> p4 = Pair.create((View) tvPhoneNo, getString(R.string.transition_tv_phone_no));
        Pair<View, String> p5 = Pair.create((View) llPhone, getString(R.string.transition_ll_phone));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p2, p3, p4, p5);
        startActivity(intent, options.toBundle());
    }

    public void onRegistrationUI(View view) {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        Pair<View, String> p2 = Pair.create((View) ivFlag, getString(R.string.transition_iv_flag));
        Pair<View, String> p3 = Pair.create((View) tvCode, getString(R.string.transition_tv_code));
        Pair<View, String> p4 = Pair.create((View) tvPhoneNo, getString(R.string.transition_tv_phone_no));
        Pair<View, String> p5 = Pair.create((View) llPhone, getString(R.string.transition_ll_phone));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p2, p3, p4, p5);
        startActivity(intent, options.toBundle());
    }

    @Override
    public void onBackPressed() {

    }
}