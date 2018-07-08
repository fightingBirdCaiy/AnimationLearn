package com.caiy.learn.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mHintTextView;
    private int viewWidth;
    private int viewHeight;
    private ViewTreeObserver.OnGlobalLayoutListener listener;
    private DisplayMetrics metrics;
    private float density;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mHintTextView = (TextView)findViewById(R.id.hint_tv);
        mHintTextView = (TextView)findViewById(R.id.hint_outer_rl_tv);
        metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        Toast.makeText(this,"density=" + density,Toast.LENGTH_SHORT).show();

        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHintTextView.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
                viewWidth = mHintTextView.getMeasuredWidth();// - mHintTextView.getPaddingLeft() - mHintTextView.getPaddingRight();
                viewHeight = mHintTextView.getMeasuredHeight();

//                fakeLoad();

                loadStartAnimation(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mHintTextView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadDisappareAnimation();
                            }
                        },1000);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                },0f,1.1f,1f,1.1f,1f);
            }
        };
        mHintTextView.getViewTreeObserver().addOnGlobalLayoutListener(listener);
    }

    private void fakeLoad(){
        mHintTextView.setVisibility(View.VISIBLE);
        loadStartAnimationInner(0.3f);
    }

    private void loadStartAnimationInner(float ratio){
        mHintTextView.setScaleX(ratio);
        mHintTextView.setScaleY(ratio);
        float translationX = -(viewWidth-viewWidth*ratio)/2 + dpToPix(140) - dpToPix(140)*ratio;
        mHintTextView.setTranslationX(translationX);
        float tranlationY = (viewHeight-viewHeight*ratio)/2;
        mHintTextView.setTranslationY(tranlationY);
    }

    private void loadStartAnimation(Animator.AnimatorListener listener, float... values ){
        ValueAnimator startAnimator = ValueAnimator.ofFloat(values);
        startAnimator.setDuration(5000);
        startAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float currentValue = (float)valueAnimator.getAnimatedValue();
                loadStartAnimationInner(currentValue);
            }
        });
        if(listener != null) {
            startAnimator.addListener(listener);
        }
        startAnimator.start();
        mHintTextView.setVisibility(View.VISIBLE);

    }

    private void loadDisappareAnimation(){
        loadStartAnimation(null,1f,0f);
    }

    private float dpToPix(float dp){
        return dp*density;
    }
}
