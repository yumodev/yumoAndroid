/**
 * AnimationDemoFragment.java
 * yumodev
 * 2014-12-4
 */
package com.yumo.android.test.media.animation;

import com.yumo.android.R;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ToggleButton;

/**
 * yumodev
 */
public class AnimationDemoFragment extends Fragment{
    private final String TAG = "AnimationDemoFragment";
    private Spinner mSpinner = null;
    private ToggleButton mToggleBtn = null;
    private ImageView mImageView = null;

    private final int ANIM_XML = 0;
    private final int ANIM_API = 1;
    private int mAnimType = ANIM_XML;

    private final String ANIM_ALPHA = "alpha";
    private final String ANIM_ROTATE = "rotate";
    private final String ANIM_TRANSLATE = "translate";
    private final String ANIM_SCALE = "scale";
    private final String ANIM_SET = "set";

    private String[] mAnims = {ANIM_ALPHA, ANIM_ROTATE, ANIM_TRANSLATE, ANIM_SCALE, ANIM_SET};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.animation_demo_page, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * 初始化界面
     * yumo
     *
     * @return boolean
     * 2014-12-3
     */
    private boolean initView() {
        mImageView = (ImageView) getView().findViewById(R.id.img);

        mToggleBtn = (ToggleButton) getView().findViewById(R.id.toggleBtn);
        mToggleBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mToggleBtn.isChecked()) {
                    mAnimType = ANIM_API;
                } else {
                    mAnimType = ANIM_XML;
                }
                Log.d(TAG, "toggleBtn:animType:" + mAnimType);
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mAnims);
        mSpinner = (Spinner) getView().findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Animation anim = null;
                if (mAnims[position].equals(ANIM_ALPHA)) {
                    if (mAnimType == ANIM_XML) {
                        anim = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_demo);
                    } else if (mAnimType == ANIM_API) {
                        anim = getAlpha();
                    }
                } else if (mAnims[position].equals(ANIM_ROTATE)) {
                    if (mAnimType == ANIM_XML) {
                        anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_demo);
                    } else if (mAnimType == ANIM_API) {
                        anim = getRotate();
                    }
                } else if (mAnims[position].equals(ANIM_SCALE)) {
                    if (mAnimType == ANIM_XML) {
                        anim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_demo);
                    } else if (mAnimType == ANIM_API) {
                        anim = getScale();
                    }
                } else if (mAnims[position].equals(ANIM_TRANSLATE)) {
                    if (mAnimType == ANIM_XML) {
                        anim = AnimationUtils.loadAnimation(getContext(), R.anim.translate_demo);
                    } else if (mAnimType == ANIM_API) {
                        anim = getTranslate();
                    }
                } else if (mAnims[position].equals(ANIM_SET)) {
                    if (mAnimType == ANIM_XML) {
                        anim = AnimationUtils.loadAnimation(getContext(), R.anim.set_demo);
                    } else if (mAnimType == ANIM_API) {
                        anim = getSet();
                    }
                }
                if (anim != null) {
                    mImageView.startAnimation(anim);
                    anim.setAnimationListener(new AnimationListener() {

                        @Override
                        public void onAnimationStart(Animation animation) {
                            Log.d(TAG, "onAnimationStart");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            Log.d(TAG, "onAnimationRepeat");
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Log.d(TAG, "onAnimationEnd");
                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

    private Animation getAlpha() {
        AlphaAnimation anim = new AlphaAnimation(1, 0);
        anim.setDuration(1000);
        anim.setRepeatCount(10);
        anim.setFillAfter(false);
        return anim;
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
}
