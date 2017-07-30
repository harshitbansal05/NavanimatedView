package com.example.dell.navanimatedview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.FloatArrayEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.graphics.drawable.Animatable;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ImageView imageView1, imageView2;
    private boolean isFirstImage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    /**
     * method to scale and bounce the image
     */
    public void scaleAndBounce(View v) {
        // AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.bounce);
        // animatorSet.setTarget(imageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        imageView1.startAnimation(animation);
    }

    /**
     * method to rotate the image
     * @param v View associated with the button
     */
    public void rotate(View v){
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        imageView1.startAnimation(animation);
    }

    /**
     * initialise views of the layout
     */
    private void initViews() {
        text = (TextView) findViewById(R.id.text);
        imageView1 = (ImageView) findViewById(R.id.image1);
        imageView2 = (ImageView) findViewById(R.id.image2);
    }

    /**
     * simple method to flip the image
     * this results in bad effects of transition
     */
    private void flipImage(){
        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip);
        animator.setTarget(imageView1);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    /**
     * method to scale and translate the image
     * @param view View associated with the button
     */
    public void scaleAndTranslate(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_scale);
        text.startAnimation(animation);
    }

    /**
     * method to flip the images
     * @param view View associated with the flip button
     */
    public void flipImages(View view) {
        if (isFirstImage) {
            applyRotation(0, 90);
            isFirstImage = !isFirstImage;
        } else {
            applyRotation(0, 90);
            isFirstImage = !isFirstImage;
        }
    }

    private void applyRotation(final float start, final float end) {

        ObjectAnimator  objectAnimator;
        if (isFirstImage)
            objectAnimator = ObjectAnimator.ofObject(imageView1, "rotationY", new FloatEvaluator(), start, end);
        else
            objectAnimator = ObjectAnimator.ofObject(imageView2, "rotationY", new FloatEvaluator(), start, end);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView1.post(new SwapViews());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private final class SwapViews implements Runnable{

        @Override
        public void run() {
            if (isFirstImage){
                imageView2.setVisibility(View.GONE);
                imageView1.setVisibility(View.VISIBLE);
                imageView1.requestFocus();
            }
            else{
                imageView1.setVisibility(View.GONE);
                imageView2.setVisibility(View.VISIBLE);
                imageView2.requestFocus();
            }
            if (isFirstImage){
                ObjectAnimator  objectAnimator = ObjectAnimator.ofObject(imageView1, "rotationY", new FloatEvaluator(), 90, 0);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
            } else{
                ObjectAnimator  objectAnimator = ObjectAnimator.ofObject(imageView2, "rotationY", new FloatEvaluator(), 90, 0);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
            }
        }
    }
}
