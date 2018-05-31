package common.utils.base.activity;

import android.view.View;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import common.utils.R;
import common.utils.databinding.ActivityCommonBaseBackBinding;

/**
 * @author wd
 * @date 2018/04/27
 * Email 18842602830@163.com
 * Description
 */

public abstract class CommonBaseBackActivity extends CommonBaseActivity {

    private ActivityCommonBaseBackBinding mBinding;

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
        overridePendingTransition(R.anim.slide_from_right, R.anim.keep_anim);
        mBinding = getDataBinding(R.layout.activity_common_base_back);
        contentView = mBinding.getRoot();
        setContentView(contentView);
        initStatusBar(setStatusBarColor(), isShowStatusBar(), showTopBlackFont());
        if (getTitleView() != null) {
            mBinding.container.addView(getTitleView());
        }
        mBinding.container.addView(view);
    }

    protected boolean addSlidr() {
        return true;
    }

    /**
     * 设置头视图
     */
    protected abstract View getTitleView();

}
