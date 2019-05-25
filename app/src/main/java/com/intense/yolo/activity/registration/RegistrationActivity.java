package com.intense.yolo.activity.registration;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.intense.yolo.R;

import es.dmoral.toasty.Toasty;

import static android.view.Gravity.LEFT;

public class RegistrationActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvMoving;
    private LinearLayout llPhone;
    private FrameLayout flRegistration;
    private FABProgressCircle fabProgressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        flRegistration = (FrameLayout) findViewById(R.id.fl_registration);
        fabProgressCircle = (FABProgressCircle) findViewById(R.id.fab_progress_circle);
        llPhone = (LinearLayout) findViewById(R.id.ll_phone);
        tvMoving = (TextView) findViewById(R.id.tv_moving);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startReturnTransition();
            }
        });
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getApplicationContext(), "Coming soon", Toast.LENGTH_SHORT, true).show();
            }
        });
        setupWindowAnimations();

      /*  ivBacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  vfRegistration.showPrevious();
            }
        });*/
        /*slideRight = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_right);
        slideLeft = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_left);
        tvYoloRideRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///vfRegistration.setInAnimation(slideRight);
                //  vfRegistration.setOutAnimation(slideLeft);
                //vfRegistration.showNext();
            }
        });*/
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

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_right);
            ivBack.startAnimation(animation);
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
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            Animation animation = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_left);
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
            flRegistration.setAlpha(1f);
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

    @Override
    public void onBackPressed() {
        startReturnTransition();
    }

    private void startReturnTransition() {
        super.onBackPressed();
    }
}