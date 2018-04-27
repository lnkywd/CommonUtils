package common.utils.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import common.utils.R;
import common.utils.utils.ColorUtils;
import common.utils.utils.Handlers;


/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class ProgressDialog extends Dialog {


    private Context mContext = null;
    private LinearLayout rootPanel;
    private ImageView mImg = null;
    private ProgressBar progressBar = null;
    private TextView mTxt = null;
    private Animation animation = null;
    private AnimationDrawable animationDrawable = null;
    private Animator animator;

    public ProgressDialog(Context context) {
        super(context, R.style.hd_progress_dialog);
        mContext = context;
        init();
    }

    public void init() {
        View dialogContainer = View.inflate(mContext, R.layout.i_progress_dialog,
                null);
        rootPanel = dialogContainer.findViewById(R.id.rootPanel);
        mImg = dialogContainer.findViewById(R.id.imageView);
        progressBar = dialogContainer.findViewById(R.id.progressBar);
        mTxt = dialogContainer.findViewById(R.id.textView);
        setContentView(dialogContainer);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        init();
    }

    public ProgressDialog withMsg(CharSequence msg) {
        mTxt.setText(msg);
        mTxt.setVisibility(View.VISIBLE);
        return this;
    }

    public ProgressDialog withMsg(int resId) {
        mTxt.setText(mContext.getString(resId));
        mTxt.setVisibility(View.VISIBLE);
        return this;
    }

    public ProgressDialog withBgColor(int color) {
        if (color == Color.TRANSPARENT) {
            rootPanel.setBackgroundColor(color);
        } else {
            rootPanel.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        }
        return this;
    }

    public ProgressDialog withBgColor(String colorString) {
        rootPanel.getBackground().setColorFilter(ColorUtils.getColorFilter(Color.parseColor
                (colorString)));
        return this;
    }

    public ProgressDialog frameAnim(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progressBar.setIndeterminateDrawable(mContext.getDrawable(resId));
            progressBar.setVisibility(View.VISIBLE);
        } else {
            mImg.setImageResource(resId);
            mImg.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            animationDrawable = (AnimationDrawable) mImg.getDrawable();
        }
        return this;
    }

    public ProgressDialog animator(int res, final Object img) {
        animator = AnimatorInflater.loadAnimator(getContext(), res);
        animator.setTarget(mImg);
        Handlers.sharedHandler(getContext()).post(new Runnable() {
            @Override
            public void run() {
                Glide.with(getContext()).load(img).into(mImg);
            }
        });
        mImg.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        return this;
    }

    public ProgressDialog tweenAnim(int drawable, int anim) {
        mImg.setImageResource(drawable);
        mImg.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        animation = AnimationUtils.loadAnimation(mContext, anim);
        return this;
    }

    public ProgressDialog withTypeface(Typeface typeface) {
        mTxt.setTypeface(typeface);
        return this;
    }

    public ProgressDialog cancelable(boolean cancelable) {
        setCancelable(cancelable);
        return this;
    }

    public ProgressDialog outsideCancelable(boolean outsideCancelable) {
        setCanceledOnTouchOutside(outsideCancelable);
        return this;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        animation = null;
        animationDrawable = null;
        if (animator != null) {
            animator.cancel();
            animator.end();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        if (animation != null) {
            mImg.startAnimation(animation);
        }
        if (animator != null) {
            animator.start();
        }
    }

}
