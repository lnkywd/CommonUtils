package common.utils.base.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import common.utils.R;
import common.utils.databinding.ActivityBaseBackScrollBinding;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public abstract class BaseBackScrollActivity extends BaseActivity {
    protected ActivityBaseBackScrollBinding baseBinding;

    @Override
    protected void setLayout(View view) {
        SlidrConfig.Builder mBuilder = new SlidrConfig.Builder()
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .edge(true)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        Slidr.attach(this,  mBuilder.build());
        baseBinding = getDataBinding(R.layout.activity_base_back_scroll);
        setContentView(baseBinding.getRoot());
        contentView = baseBinding.getRoot();
        setSupportActionBar(baseBinding.toolbar);
        initStatusBar(setStatusBarColor(), isShowStatusBar());

        baseBinding.toolbar.setNavigationIcon(R.mipmap.icon_train_register_back);
        baseBinding.barTitleShare.setVisibility(View.GONE);

//        getToolBar().setDisplayHomeAsUpEnabled(true);
        getToolBar().setDisplayShowTitleEnabled(false);
        baseBinding.activityContainer.addView(view);

//        baseBinding.refreshLayout.setEnableAutoLoadmore(false);
//        baseBinding.refreshLayout.setOnRefreshListener(setRefreshListener());
//        baseBinding.refreshLayout.setOnLoadmoreListener(setLoadmoreListener());
    }

    @Override
    public void setTitle(CharSequence title) {
        setTitleText(title.toString());
    }

    @Override
    public void setTitle(int titleId) {
        setTitleText(getResources().getString(titleId));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }

    protected void setTitleText(String text) {
        baseBinding.barTitleTv.setText(text);
    }

    protected void setTitleTextColor(@ColorRes int color){
        baseBinding.barTitleTv.setTextColor(getResources().getColor(color));
    }
    /**
     * 显示右侧分享图标
     */
    protected void showRightImageView() {
        baseBinding.barTitleShare.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧按钮 图片
     *
     * @param drawableId DrawableRes
     */
    protected void setRightImageView(@DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(mContext, drawableId);
        baseBinding.barTitleShare.setImageDrawable(drawable);
    }

    protected void setLiftBackBackground(@DrawableRes int id) {
        baseBinding.toolbar.setNavigationIcon(id);
    }

    /**
     * 设置右侧按钮点击事件
     *
     * @param click {@link View.OnClickListener}
     */
    protected void setRightImageViewClick(View.OnClickListener click) {
        baseBinding.barTitleShare.setOnClickListener(click);
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
//    protected abstract OnLoadmoreListener setLoadmoreListener();
//
//    protected abstract OnRefreshListener setRefreshListener();

}
