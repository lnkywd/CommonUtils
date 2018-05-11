package common.utils.base.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import common.utils.R;
import common.utils.databinding.ActivityBaseBackBinding;
import common.utils.view.ViewClick;


/**
 * Created by Administrator on 2017/11/2 0002.
 */

public abstract class CommonBaseBackToolbarActivity extends CommonBaseActivity {
    protected ActivityBaseBackBinding baseBinding;

    @Override
    protected void setLayout(View view) {
        SlidrConfig.Builder mBuilder = new SlidrConfig.Builder()
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .edge(true)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        if (addSlidr()) {
            Slidr.attach(this, mBuilder.build());
        }
        baseBinding = getDataBinding(R.layout.activity_base_back);
        setContentView(baseBinding.getRoot());
        contentView = baseBinding.getRoot();
        setSupportActionBar(baseBinding.toolbar);
        initStatusBar(setStatusBarColor(), isShowStatusBar(), showTopBlackFont());

//        baseBinding.toolbar.setNavigationIcon(R.mipmap.icon_train_register_back);
        baseBinding.barTitleShare.setVisibility(View.GONE);
        baseBinding.barTitleRightTv.setVisibility(View.GONE);

//        getToolBar().setDisplayHomeAsUpEnabled(true);
        getToolBar().setDisplayShowTitleEnabled(false);
//        int height = unDisplayViewSize(baseBinding.abl)[1];
//        view.setPadding(0,0,0,height);
        baseBinding.activityContainer.addView(view);
    }

    protected boolean addSlidr() {
        return true;
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }

    public void setBackgroundColor(int color) {
        baseBinding.toolbar.setBackgroundColor(color);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (backClick()) {
            return super.onOptionsItemSelected(item);
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitleText(title.toString());
    }

    @Override
    public void setTitle(int titleId) {
        setTitleText(getResources().getString(titleId));
    }

    protected void setTitleText(String text) {
        baseBinding.barTitleTv.setText(text);
    }

    protected boolean backClick() {
        return false;
    }

    protected void setTitleTextColor(@ColorRes int color) {
        baseBinding.barTitleTv.setTextColor(getResources().getColor(color));
    }

    protected void setCommonTitleText(String text) {
        baseBinding.barTitleTv.setTextColor(titleColor());
        setLiftBackBackground(imgRes());
        baseBinding.barTitleTv.setText(text);
    }

    /**
     * 设置标题的颜色
     */
    protected abstract int titleColor();

    protected void setLiftBackBackground(@DrawableRes int id) {
//        baseBinding.toolbar.setNavigationIcon(id);
        baseBinding.back.setImageResource(id);
        baseBinding.back.setOnClickListener(new ViewClick() {
            @Override
            public void onViewClick(View view) {
                if (backClick()) {
                    return;
                }
                finish();
            }
        });
    }

    /**
     * 设置返回的图标
     */
    protected abstract int imgRes();

    /**
     * 隐藏右侧文字
     */
    protected void hideRightTextView() {
        baseBinding.barTitleRightTv.setVisibility(View.GONE);
    }

    /**
     * 设置右侧按钮 图片
     *
     * @param drawableId DrawableRes
     */
    protected void setRightImage(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        baseBinding.barTitleShare.setImageDrawable(drawable);
        showRightImageView();
    }

    /**
     * 显示右侧图标
     */
    protected void showRightImageView() {
        baseBinding.barTitleShare.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧按钮 文字
     *
     * @param text String
     */
    protected void setRightText(String text) {
        baseBinding.barTitleRightTv.setText(text);
        showRightTextView();
    }

    /**
     * 显示右侧文字
     */
    protected void showRightTextView() {
        baseBinding.barTitleRightTv.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧按钮 文字
     *
     * @param color ColorRes
     */
    protected void setRightTextColor(@ColorRes int color) {
        baseBinding.barTitleRightTv.setTextColor(ContextCompat.getColor(this, color));
    }

    /**
     * 设置右侧按钮点击事件
     *
     * @param click {@link View.OnClickListener}
     */
    protected void setRightClick(ViewClick click) {
        baseBinding.barTitleShare.setOnClickListener(click);
        baseBinding.barTitleRightTv.setOnClickListener(click);
    }

    protected TextView getTitleTv() {
        return baseBinding.barTitleTv;
    }

    protected ImageView getLeftIv() {
        return baseBinding.back;
    }

    protected Toolbar getToolbar() {
        return baseBinding.toolbar;
    }

    /**
     * 设置toolBar背景
     *
     * @param drawableId DrawableRes
     */
    protected void setTitleBackground(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        getToolBar().setBackgroundDrawable(drawable);
    }

    /**
     * 计算一个view的宽高
     *
     * @param view 需要计算的view
     * @return 0 宽 、1 高
     */
    private int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }

}
