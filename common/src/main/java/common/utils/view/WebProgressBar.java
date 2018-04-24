package common.utils.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import common.utils.R;


/**
 * webview 顶部进度条加载
 */
public class WebProgressBar extends View {

    private int mCurProgress;//当前的进度
    private float mDrawProgress = 0;// 当前绘制的进度
    private int mHeight;
    private int mWidth;
    private Paint mPaint;

    private OnEndListener mListener;
    // 是否已经加载完成
    private boolean mHasLoad = false;

    public WebProgressBar(Context context) {
        this(context, null);
    }

    public WebProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(ContextCompat.getColor(getContext(), R.color.a4b94ee));
    }

    public void setOnEndListener(OnEndListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getVisibility() == View.GONE) {
            return;
        }
        float result = mWidth * (mDrawProgress / 100);
        canvas.drawRect(0, 0, result, mHeight, mPaint);
        if (mDrawProgress > 5 && mHasLoad) {
            setFinishProgress();
            mHasLoad = false;
        }
        if (mCurProgress == 100 && mDrawProgress > 10) {
            return;
        }
        if (mDrawProgress >= mCurProgress) {
            if (mDrawProgress >= 88) {
                return;
            }
            if (mDrawProgress > 60) {
                mDrawProgress += 0.05f;
            } else {
                mDrawProgress += 0.2f;
            }
        } else {
            mDrawProgress += 0.5f;
        }
        invalidate();
    }

    public void setFinishProgress() {
        if (mDrawProgress <= 5) {
            mHasLoad = true;
            return;
        }
        //注意是从 mCurProgress->curProgress 来动画来实现
        ValueAnimator animator = ValueAnimator.ofFloat(mDrawProgress, 95f);
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawProgress = (float) animation.getAnimatedValue();
                postInvalidate();//通知刷新
            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hideProgress();
            }


            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void hideProgress() {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);
        ValueAnimator animator = ValueAnimator.ofFloat(mDrawProgress, 100f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawProgress = (float) animation.getAnimatedValue();
                postInvalidate();//通知刷新
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(alphaAnimator);
        animatorSet.setDuration(400);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mListener != null) {
                    mListener.onEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public void setDrawProgress() {
        this.mDrawProgress = 0;
        invalidate();
    }

    public void setNormalProgress(int newProgress) {
        mCurProgress = newProgress;
    }

    public interface OnEndListener {
        void onEnd();//动画结束的回调
    }


}
