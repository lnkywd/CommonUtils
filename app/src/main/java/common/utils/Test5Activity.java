package common.utils;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import common.utils.databinding.ActivityTest5Binding;

/**
 * @author wd
 * @date 2018/05/25
 * Email 18842602830@163.com
 * Description
 */

public class Test5Activity extends AppCompatActivity {

    private ActivityTest5Binding mBinding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, Test5Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test5);

//        mBinding.baseWebView.setUrl("http://www.baidu.com");
        mBinding.baseWebView.setUrl("http://m.ajihua888.com/shop-point/detail?id=1887");
    }

    public void click(View view) {
        mBinding.webView.getHtml();
    }

}
