/**
 * AnimationDemoFragment.java
 * yumo
 * 2014-12-4
 * TODO
 */
package com.yumo.android.test.media;

import com.yumo.android.R;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class ViewAnimationDemoActivity extends Activity implements View.OnClickListener {
    private final String TAG = "ViewAnimation";
    private Spinner mSpinner = null;
    private ToggleButton mToggleBtn = null;
    private ImageView mImageView = null;

    private final int ANIM_XML = 0;
    private final int ANIM_API = 1;
    private int mAnimType = ANIM_XML;

    private final String ANIM_ALPHA = "alpha";
    private final String ANIM_WIDTH = "width";

    private final String ANIM_ROTATE = "rotate";
    private final String ANIM_TRANSLATE = "translate";
    private final String ANIM_SCALE = "scale";
    private final String ANIM_SET = "set";

    private String[] mAnims = {ANIM_ALPHA, ANIM_WIDTH, ANIM_ROTATE, ANIM_TRANSLATE, ANIM_SCALE, ANIM_SET};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.animation_demo_page);
        initView();
    }

    private boolean initView() {
        ImageView backImg = (ImageView) findViewById(R.id.back_img);
        backImg.setVisibility(View.VISIBLE);
        backImg.setOnClickListener(this);

        TextView titleText = (TextView) findViewById(R.id.title);
        titleText.setText("ViewAnimation");

        mImageView = (ImageView) findViewById(R.id.img);

        mToggleBtn = (ToggleButton) findViewById(R.id.toggleBtn);
        mToggleBtn.setOnClickListener(new OnClickListener() {

            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mToggleBtn.isChecked()) {
                    mAnimType = ANIM_API;
                } else {
                    mAnimType = ANIM_XML;
                }
                Log.d(TAG, "toggleBtn:animType:" + mAnimType);
            }
        });


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mAnims);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                if (mAnims[position].equals(ANIM_ALPHA)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.alpha_demo);
                    } else if (mAnimType == ANIM_API) {
                        getAlpha();
                    }
                } else if (mAnims[position].equals(ANIM_WIDTH)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.alpha_demo);
                    } else if (mAnimType == ANIM_API) {
                        getAnimWidth();
                    }
                } else if (mAnims[position].equals(ANIM_ROTATE)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.rotate_demo);
                    } else if (mAnimType == ANIM_API) {
                        getRotate();
                    }
                } else if (mAnims[position].equals(ANIM_SCALE)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.scale_demo);
                    } else if (mAnimType == ANIM_API) {
                        getScale();
                    }
                } else if (mAnims[position].equals(ANIM_TRANSLATE)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.translate_demo);
                    } else if (mAnimType == ANIM_API) {
                        getTranslate();
                    }
                } else if (mAnims[position].equals(ANIM_SET)) {
                    if (mAnimType == ANIM_XML) {
                        AnimationUtils.loadAnimation(ViewAnimationDemoActivity.this, R.anim.set_demo);
                    } else if (mAnimType == ANIM_API) {
                        getSet();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        mSpinner.setSelection(0);
        return true;
    }

    @SuppressLint("NewApi")
    private Animation getAlpha() {

        ObjectAnimator animator = ObjectAnimator.ofFloat(mImageView, "alpha", 1.0f, 0.3f, 1.0F);
        animator.setDuration(2000);
        animator.setInterpolator(new BounceInterpolator());
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setStartDelay(1000);
        animator.start();
        return null;
    }

    private void getAnimWidth() {
//		ViewWrapper wrapper = new ViewWrapper(mImageView);
//		mImageView.getLayoutParams().width = 0;
//		mImageView.requestLayout();
//		ObjectAnimator.ofInt(wrapper,"width",1000).setDuration(10000).start();

        performAnimateWidth(mImageView, 0, 1000);
    }


    private void performAnimateWidth(final View target, final int start, final int end) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);


        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (Integer) valueAnimator.getAnimatedValue();

                float fraction = currentValue / 100f;
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.getLayoutParams().height = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(2000).start();
    }


    private Animation getTranslate() {
        TranslateAnimation anim = new TranslateAnimation(0, -50, 0, -100);
        anim.setDuration(1000);
        anim.setFillAfter(false);
        return anim;
    }

    private Animation getScale() {
        ScaleAnimation anim = new ScaleAnimation(1, 0, 1, 0, 0.5f, 0.5f);
        anim.setDuration(1000);
        anim.setFillAfter(false);
        return anim;
    }

    private Animation getRotate() {
        RotateAnimation anim = new RotateAnimation(0, 270, 0.5f, 0.5f);
        anim.setDuration(1000);
        anim.setFillAfter(false);
        return anim;
    }


    private Animation getSet() {
        AnimationSet anim = new AnimationSet(false);
        anim.setDuration(2000);
        anim.setFillAfter(true);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(1000);
        alpha.setFillAfter(true);

        RotateAnimation rotate = new RotateAnimation(0, 270, 0.5f, 0.5f);
        rotate.setStartOffset(1000);
        rotate.setDuration(1000);
        rotate.setFillAfter(true);

        anim.addAnimation(alpha);
        anim.addAnimation(rotate);

        return anim;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, TAG + "onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, TAG + "onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, TAG + "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, TAG + "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, TAG + "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Log.d(TAG, TAG + "onClick v.id:" + v.getId());
        switch (v.getId()) {
            case R.id.back_img: {
                Log.d(TAG, TAG + "onclick backimg");
                finish();
                break;
            }
        }
    }

    private class ViewWrapper {
        private View mTarget = null;

        public ViewWrapper(View target) {
            mTarget = target;
        }

        public int getWidth() {
            if (mTarget != null) {
                return mTarget.getLayoutParams().width;
            }

            return 0;
        }

        public void setWidth(int width) {
            if (mTarget != null) {
                mTarget.getLayoutParams().width = width;
                mTarget.requestLayout();
            }
        }
    }


}
