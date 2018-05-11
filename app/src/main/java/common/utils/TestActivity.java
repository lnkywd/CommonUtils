package common.utils;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import common.utils.databinding.TestActivityBinding;
import common.utils.utils.GlideUtils;
import common.utils.view.SmoothImageView;

/**
 * @author wd
 * @date 2018/04/28
 * Email 18842602830@163.com
 * Description
 */

public class TestActivity extends AppCompatActivity {

    private TestActivityBinding binding;

    public static void launch(Context context, Rect rect) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("rect", rect);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.test_activity);
        Rect rect = getIntent().getExtras().getParcelable("rect");
        GlideUtils.displayImage(binding.iv, "http://img3.duitang.com/uploads/item/201503/14/20150314212812_kCLmy.thumb.700_0.jpeg");
        binding.iv.setThumbRect(rect);
        binding.iv.transformIn(new SmoothImageView.onTransformListener() {
            @Override
            public void onTransformCompleted(SmoothImageView.Status status) {

            }
        });
    }
}
