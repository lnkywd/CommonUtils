package common.utils.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import common.utils.R;
import common.utils.utils.BarUtils;
import common.utils.utils.ProgressDialogUtils;


public abstract class BaseActivity extends RxAppCompatActivity implements BaseView {

    protected View contentView;
    protected BaseActivity mActivity;
    protected Context mContext;

    private boolean showStatusBar = false;

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
        overridePendingTransition(R.anim.slide_from_right,R.anim.keep_anim);
        setContentView(view);
        contentView = view;
        initStatusBar(setStatusBarColor(), isShowStatusBar());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.slide_from_right,R.anim.keep_anim);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.slide_from_right,R.anim.keep_anim);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProgressDialogUtils.dismissDialog();
    }

    public <T extends ViewDataBinding> T getDataBinding(int layoutId) {
        T t = DataBindingUtil.inflate(getLayoutInflater(), layoutId, null, false);
        return t;
    }

    protected void initStatusBar(@ColorRes int color, boolean isShow) {
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (color == 0 || color == R.color.app_white_bar_color) {
                    color = R.color.white;
                }
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
                contentView.setFitsSystemWindows(true);
                if (color == R.color.white)
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                else {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                }
            } else {
                if (color == 0) {
                    color = R.color.app_white_bar_color;
                }
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
                BarUtils.addMarginTopEqualStatusBarHeight(contentView);
            }
        } else {
            if (color == 0) {
                color = R.color.app_white_bar_color;
            }
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
        }
    }
}
