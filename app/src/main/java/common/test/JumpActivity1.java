package common.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.List;

import common.test.db.model.TestModel;
import common.utils.R;
import common.utils.base.activity.CommonBaseActivity;
import common.utils.databinding.ActivityJump1Binding;
import common.test.db.DbHelper;
import common.utils.utils.RxTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author wd
 * @date 2018/05/17
 * Email 18842602830@163.com
 * Description
 */

@Route(path = "/test/jump1")
public class JumpActivity1 extends CommonBaseActivity {

    private ActivityJump1Binding mBinding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, JumpActivity1.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean showTopBlackFont() {
        return true;
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            Log.i("wdwdwd", "11===" + bundle.getString("1"));
            Log.i("wdwdwd", "22===" + bundle.getInt("2"));
            Log.i("wdwdwd", "33===" + bundle.getBoolean("3"));
        }
    }

    @NonNull
    @Override
    public View bindLayout() {
        mBinding = getDataBinding(R.layout.activity_jump1);
        return mBinding.getRoot();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mBinding.number.setDuration(1000);
        mBinding.number.setNumberString("10000.56");
        mBinding.number.setPrefixString("$");

        DbHelper.getInstance().test().loadAll()
                .compose(this.<List<TestModel>>bindToLifecycle())
                .compose(RxTransformer.<List<TestModel>>switchSchedulers())
                .subscribe(new Observer<List<TestModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<TestModel> testModels) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public int setStatusBarColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public boolean isShowStatusBar() {
        return true;
    }
}
