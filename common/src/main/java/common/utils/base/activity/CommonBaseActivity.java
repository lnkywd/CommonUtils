package common.utils.base.activity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import common.utils.R;
import common.utils.utils.BarUtils;


public abstract class CommonBaseActivity extends RxAppCompatActivity implements BaseView {

    protected View contentView;
    protected CommonBaseActivity mActivity;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        setLayout(bindLayout());
        initData(bundle);
        if (isFinishing()) {
            return;
        }
        initView(savedInstanceState);
        doBusiness();
    }

    protected void setLayout(View view) {
        overridePendingTransition(R.anim.slide_from_right, R.anim.keep_anim);
        setContentView(view);
        contentView = view;
        initStatusBar(setStatusBarColor(), isShowStatusBar(), showTopBlackFont());
    }

    protected void initStatusBar(@ColorRes int color, boolean isShow, boolean showTopBlack) {
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
                contentView.setFitsSystemWindows(true);
                if (showTopBlack)
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            } else {
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
                BarUtils.addMarginTopEqualStatusBarHeight(contentView);
            }
        } else {
            color = R.color.transparent;
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && showTopBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    /**
     * 是否高亮（黑色）
     */
    protected abstract boolean showTopBlackFont();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public <T extends ViewDataBinding> T getDataBinding(int layoutId) {
        T t = DataBindingUtil.inflate(getLayoutInflater(), layoutId, null, false);
        t.getRoot().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return t;
    }

}
