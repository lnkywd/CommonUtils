package common.utils.base.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.view.View;


interface BaseView {

    /**
     * 初始化数据
     *
     * @param bundle 传递过来的bundle
     */
    void initData(final Bundle bundle);

    /**
     * 绑定布局
     *
     * @return 布局Id
     */
    @NonNull
    View bindLayout();

    /**
     * 初始化view
     */
    void initView(final Bundle savedInstanceState);

    /**
     * 业务操作
     */
    void doBusiness();

    /**
     * 初始化状态栏
     */
    @ColorRes
    int setStatusBarColor();

    /**
     * 是否沉侵
     */
    boolean isShowStatusBar();

}
