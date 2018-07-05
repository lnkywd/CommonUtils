package common.utils.base.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

import common.utils.utils.FragmentUtils;
import common.utils.utils.LogUtils;


public abstract class BaseFragment extends RxFragment
        implements BaseView, FragmentUtils.OnBackClickListener {

    private static final String TAG = "BaseFragment";

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;


    protected Activity mActivity;
    protected Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        LogUtils.d(TAG, "onCreate: ");
        mContext = mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        initData(bundle);
        initView(savedInstanceState);
        LogUtils.d(TAG, "onViewCreated: ");
    }

    @Override
    public void onDestroyView() {
        if (contentView != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }
        if (isRegisterEvent() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
        LogUtils.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy: ");
    }

    protected boolean isRegisterEvent() {
        return false;
    }

    @SuppressLint("ResourceType")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isRegisterEvent() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        LogUtils.d(TAG, "onCreateView: ");
        setRetainInstance(true);
        View view = bindLayout();
        // viewgroup 代码 addview ，必须在 fragment 成型之前
        onAddViewInit();
        return view;
    }

    public void onAddViewInit() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doBusiness();
        LogUtils.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public int setStatusBarColor() {
        return 0;
    }

    @Override
    public boolean isShowStatusBar() {
        return false;
    }

    public <T extends ViewDataBinding> T getDataBinding(int layoutId) {
        T t = DataBindingUtil.inflate(getLayoutInflater(), layoutId, null, false);
        return t;
    }
}
