package common.test;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import common.utils.R;
import common.utils.databinding.ActivityTest6Binding;
import common.utils.databinding.ItemTest6Binding;
import common.utils.utils.recycler.BaseDataBindingAdapter;

/**
 * @author wd
 * @date 2018/07/12
 * Email 18842602830@163.com
 * Description RecyclerView 数据测试
 */

public class Test6Activity extends AppCompatActivity {

    private ActivityTest6Binding mBinding;
    private int mNum = 0;

    public static void launch(Context context) {
        Intent intent = new Intent(context, Test6Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_from_bottom, R.anim.keep_anim);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test6);

        mBinding.rv.init(this, 0, 0, new Adapter(new ArrayList<String>()));
        mBinding.rv.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData(false);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mNum = 0;
                getData(true);
            }
        });
        mBinding.rv.autoRefresh();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.keep_anim, R.anim.slide_to_bottom);
    }

    private void getData(boolean refresh) {
        mBinding.rv.getData(refresh);
        mBinding.rv.getPage(refresh);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.rv.onHttpSuccess(true, getNums());
                mBinding.rv.onHttpComplete(false);
            }
        }, 2000);
    }

    private List<String> getNums() {
        List<String> data = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            data.add(String.valueOf(mNum + i));
            if (i == 10) {
                mNum += 10;
            }
        }
        return data;
    }

    private static class Adapter extends BaseDataBindingAdapter<String, ItemTest6Binding> {

        public Adapter(@Nullable List<String> data) {
            super(R.layout.item_test6, data);
        }

        @Override
        protected void convert(ItemTest6Binding binding, String item, int position) {
            binding.tv.setText(item);
        }
    }

}
