package com.sweet.yukun.androidsweetsheet.sweetpick;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.sweet.yukun.androidsweetsheet.SimpleAnimationListener;
import com.sweet.yukun.androidsweetsheet.entity.MenuEntity;

import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public abstract class Delegate implements View.OnClickListener {


    protected SweetSheet.Status mStatus = SweetSheet.Status.DISMISS;

    protected ViewGroup mParentVG;
    protected View mRootView;
    private ImageView mBg;
    private Effect mEffect;
    private boolean mIsBgClickEnable=true;

    protected SweetSheet.OnMenuItemClickListener mOnMenuItemClickListener;

    protected void init(ViewGroup parentVG) {
        mParentVG = parentVG;
        mBg = new ImageView(parentVG.getContext());
        mRootView = createView();
        mBg.setOnClickListener(this);
    }

    /**
     * 生成视图
     * @return
     */
    protected abstract View createView();

    /**
     * 设置数据源
     * @param list
     */
    protected abstract void setMenuList(List<MenuEntity> list);

    protected void toggle() {
        switch (mStatus) {
            case SHOW:
            case SHOWING:
                dismiss();
                break;
            case DISMISS:
            case DISMISSING:
                show();
                break;
            default:
                break;
        }
    }

    protected void show() {

        if (getStatus() != SweetSheet.Status.DISMISS) {
            return;
        }
        mBg.setClickable(mIsBgClickEnable);
        showShowdown();

    }


    /**
     * 显示模糊背景
     */
    protected void showShowdown() {

        mRootView.setTranslationY(0);
        mEffect.effect(mParentVG,mBg);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        if(mBg.getParent()!= null){
            mParentVG.removeView(mBg);
        }

        mParentVG.addView(mBg, lp);
        mRootView.setAlpha(1);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 0, 1);
        objectAnimator.setDuration(400);
        objectAnimator.start();
    }

    /**
     * 隐藏模糊背景
     */
    protected void dismissShowdown() {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBg, "alpha", 1, 0);
        objectAnimator.setDuration(400);
        objectAnimator.start();
        objectAnimator.addListener(new SimpleAnimationListener() {

            @Override
            public void onAnimationEnd(Animator animation) {

                mParentVG.removeView(mBg);
            }


        });
    }

    /**
     * 消失
     */
    protected void dismiss() {
        if (getStatus() == SweetSheet.Status.DISMISS){
            return;
        }
        mBg.setClickable(false);
        dismissShowdown();

        ObjectAnimator translationOut = ObjectAnimator.ofFloat(mRootView,
                "translationY", 0, mRootView.getHeight());
        translationOut.setDuration(600);
        translationOut.setInterpolator(new DecelerateInterpolator());
        translationOut.addListener(new SimpleAnimationListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mStatus = SweetSheet.Status.DISMISSING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = SweetSheet.Status.DISMISS;
                mParentVG.removeView(mRootView);
            }
        });
        translationOut.start();
    }


    protected void setBackgroundEffect(Effect effect) {
        mEffect=effect;
    }

    protected void setOnMenuItemClickListener(SweetSheet.OnMenuItemClickListener onItemClickListener) {
        mOnMenuItemClickListener = onItemClickListener;
    }


    /**
     * 延时消失
     */
    protected void delayedDismiss() {

        mParentVG.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 300);
    }

    protected SweetSheet.Status getStatus() {
        return mStatus;
    }

    @Override
    public void onClick(View v) {
        dismiss();


    }

    public void setBackgroundClickEnable(boolean isBgClickEnable){
        mIsBgClickEnable=isBgClickEnable;
    }
}
