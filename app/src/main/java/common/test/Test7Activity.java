package common.test;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import common.utils.R;
import common.utils.databinding.ActivityTest7Binding;
import common.utils.databinding.ItemTest71Binding;
import common.utils.databinding.ItemTest72Binding;
import common.utils.utils.recycler.BaseDataBindingAdapter;

/**
 * @author wd
 * @date 2018/07/14
 * Email 18842602830@163.com
 * Description 两个 RecyclerView 联动
 */

public class Test7Activity extends AppCompatActivity {

    private ActivityTest7Binding mBinding;
    private LinearLayoutManager mRightManager;
    private LinearLayoutManager mLeftManager;
    private boolean move = false;
    private int mPosition = 0;
    private int mCurrentLeftPosition = 0;
    private Adapter1 mAdapter1;


    public static void launch(Context context) {
        Intent intent = new Intent(context, Test7Activity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_test7);

        mBinding.rv1.setLayoutManager(mLeftManager = new LinearLayoutManager(this));
        mBinding.rv2.setLayoutManager(mRightManager = new LinearLayoutManager(this));
        List<String> data1 = new ArrayList<>();
        List<String> data2 = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data1.add("左侧 item " + i);
            data2.add("右侧 item " + i);
        }
        mBinding.rv2.addOnScrollListener(new RightRVListener());
        mBinding.rv1.setAdapter(mAdapter1 = new Adapter1(data1, new OnAdapter1ClickListener() {
            @Override
            public void click(int position) {
                mPosition = position;
                mBinding.rv2.stopScroll();
                smoothMoveToPosition();
                changeLeftSelect(position);
            }
        }));
        mBinding.rv2.setAdapter(new Adapter2(data2));
    }

    private void smoothMoveToPosition() {
        int firstItem = mRightManager.findFirstVisibleItemPosition();
        int lastItem = mRightManager.findLastVisibleItemPosition();
        Log.d("first--->", String.valueOf(firstItem));
        Log.d("last--->", String.valueOf(lastItem));
        if (mPosition <= firstItem) {
            mBinding.rv2.scrollToPosition(mPosition);
        } else if (mPosition <= lastItem) {
            Log.d("pos---->", String.valueOf(mPosition) + "VS" + firstItem);
            int top = mBinding.rv2.getChildAt(mPosition - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            mBinding.rv2.scrollBy(0, top);
        } else {
            mBinding.rv2.scrollToPosition(mPosition);
            move = true;
        }
    }

    public void changeLeftSelect(int position) {
        if (position == mCurrentLeftPosition) {
            return;
        }
        mCurrentLeftPosition = position;
        mAdapter1.setSelectPosition(mCurrentLeftPosition);

        // 将左侧当前选中的 item 居中
        View childAt = mBinding.rv1.getChildAt(position - mLeftManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - mBinding.rv1.getHeight() / 2);
            mBinding.rv1.smoothScrollBy(0, y);
        }

    }

    public interface OnAdapter1ClickListener {
        void click(int position);
    }

    private static class Adapter1 extends BaseDataBindingAdapter<String, ItemTest71Binding> {

        private OnAdapter1ClickListener mClickListener;
        private int mPosition = 0;

        public Adapter1(@Nullable List<String> data, OnAdapter1ClickListener listener) {
            super(R.layout.item_test7_1, data);
            mClickListener = listener;
        }

        @Override
        protected void convert(ItemTest71Binding binding, String item, final int position) {
            binding.tv.setText(item);
            binding.getRoot().setBackground(mPosition == position ? ContextCompat.getDrawable(mContext, R.color.color_ff6e6e)
                    : ContextCompat.getDrawable(mContext, R.color.color_a0a0a0));
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.click(position);
                }
            });
        }

        public void setSelectPosition(int position) {
            mPosition = position;
            notifyDataSetChanged();
        }
    }

    private static class Adapter2 extends BaseDataBindingAdapter<String, ItemTest72Binding> {

        public Adapter2(@Nullable List<String> data) {
            super(R.layout.item_test7_2, data);
        }

        @Override
        protected void convert(ItemTest72Binding binding, String item, int position) {
            binding.tv.setText(item);
        }
    }

    private class RightRVListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mPosition - mRightManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < mBinding.rv2.getChildCount()) {
                    int top = mBinding.rv2.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mBinding.rv2.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mPosition - mRightManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mBinding.rv2.getChildCount()) {
                    int top = mBinding.rv2.getChildAt(n).getTop();
                    mBinding.rv2.scrollBy(0, top);
                }
            } else {
                changeLeftSelect(mRightManager.findFirstVisibleItemPosition());
            }
        }
    }

}
