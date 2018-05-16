package common.utils;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import common.utils.base.activity.CommonBaseActivity;
import common.utils.databinding.ActivityMainBinding;
import common.utils.utils.apkdownload.UpdateAppUtils;

/**
 * @author wd
 * @date 2018/04/20
 * Email 18842602830@163.com
 * Description
 */

public class MainActivity extends CommonBaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected boolean showTopBlackFont() {
        return false;
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn01:
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                TestActivity.launch(this, rect);
                overridePendingTransition(0, 0);
                break;
            case R.id.btn02:
                UpdateAppUtils.from(this)
                        .serverVersionName("2.0.0")
                        .checkBy(UpdateAppUtils.CHECK_BY_OUT)
                        .isForce(true)
                        .apkPath("http://imtt.dd.qq.com/16891/AA179694CC535CE01320B7963446FEED.apk?fsname=com.qiyi.video_9.5.0_81070.apk&csr=1bbd")
                        .update();

                break;
            default:
        }
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @NonNull
    @Override
    public View bindLayout() {
        binding = getDataBinding(R.layout.activity_main);
        return binding.getRoot();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public int setStatusBarColor() {
        return R.color.color_333333;
    }

    @Override
    public boolean isShowStatusBar() {
        return false;
    }
}
