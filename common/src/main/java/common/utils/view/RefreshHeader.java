package common.utils.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import common.utils.R;

/**
 * Created by fzz on 2018/3/27 0027.
 */

public class RefreshHeader extends LinearLayout implements com.scwang.smartrefresh.layout.api.RefreshHeader {
    private ImageView mProgressView;//刷新动画视图
    private AnimationDrawable animationDrawable = null;//刷新动画
    private TextView mHeaderText;//标题文本

    public RefreshHeader(Context context) {
        super(context);
        initView(context);
    }

    public RefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public RefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        mHeaderText = new TextView(context);
        mProgressView = new ImageView(context);
        mProgressView.setImageResource(R.drawable.prg_anim_frame);
        animationDrawable = (AnimationDrawable) mProgressView.getDrawable();
        mHeaderText.setPadding(DensityUtil.dp2px(6),0,0,0);
        mHeaderText.setTextColor(Color.rgb(176,176,176));
        mHeaderText.setTextSize(13);
        addView(mProgressView, DensityUtil.dp2px(27), DensityUtil.dp2px(27));
        addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onPulling(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int height, int extendHeight) {

    }

    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {

        return 500;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                animationDrawable.start();
                mHeaderText.setText("下拉开始加载");
                break;
            case Refreshing:
                mHeaderText.setText("加载中...");
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("释放立即加载");
                break;
            case RefreshFinish:
                animationDrawable.stop();
                break;
        }
    }
}
