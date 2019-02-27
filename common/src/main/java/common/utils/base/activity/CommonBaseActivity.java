package common.utils.base.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import common.utils.R;
import common.utils.utils.BarUtils;


public abstract class CommonBaseActivity extends RxAppCompatActivity implements BaseView {

    protected View contentView;
    protected CommonBaseActivity mActivity;
    protected Context mContext;
    private String mStartActivityTag;
    private long mStartActivityTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        afterSuperOnCreate();
        mActivity = this;
        mContext = this;
        if (isRegisterEvent() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        if (isFinishing()) {
            return;
        }
        setLayout(bindLayout());
        initView(savedInstanceState);
        doBusiness();
    }

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
        if (isRegisterEvent() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    protected void afterSuperOnCreate() {

    }

    protected boolean isRegisterEvent() {
        return false;
    }

    protected void setLayout(View view) {
        setOverridePendingTransition(R.anim.slide_from_right, R.anim.keep_anim);
        setContentView(view);
        contentView = view;
        initStatusBar(setStatusBarColor(), isShowStatusBar(), showTopBlackFont());
    }

    protected void setOverridePendingTransition(int enterAnim, int exitAnim) {
        overridePendingTransition(enterAnim, exitAnim);
    }

    protected void initStatusBar(@ColorRes int color, boolean isShow, boolean showTopBlack) {
        if (!isInitStatusBar()) {
            return;
        }
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, color), 0);
                contentView.setFitsSystemWindows(true);
                if (showTopBlack) {
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else {
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

    protected boolean isInitStatusBar() {
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    /**
     * 防 Activity 多重跳转：https://www.jianshu.com/p/579f1f118161
     */

    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (startActivitySelfCheck(intent)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options);
        }
    }

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent 用于跳转的 Intent 对象
     * @return 检查通过返回true, 检查不通过返回false
     */
    protected boolean startActivitySelfCheck(Intent intent) {
        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) {
            // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) {
            // 隐式跳转
            tag = intent.getAction();
        } else { // 其他方式
            return true;
        }

        if (tag.equals(mStartActivityTag) && mStartActivityTime >= SystemClock.uptimeMillis() - minClickDelayTime()) {
            // 检查不通过
            result = false;
        }

        mStartActivityTag = tag;
        mStartActivityTime = SystemClock.uptimeMillis();
        return result;
    }

    /**
     * 两次跳转 activity 间隔
     */
    protected int minClickDelayTime() {
        return 1000;
    }

    public <T extends ViewDataBinding> T getDataBinding(int layoutId) {
        T t = DataBindingUtil.inflate(getLayoutInflater(), layoutId, null, false);
        t.getRoot().setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return t;
    }

}
