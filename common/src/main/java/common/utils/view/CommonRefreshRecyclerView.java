package common.utils.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import common.utils.utils.PixelUtils;
import common.utils.utils.recycler.BaseDataBindingAdapter;
import common.utils.utils.recycler.DividerItemBottomDecoration;

/**
 * @author wd
 * @date 2018/04/09
 * Email 18842602830@163.com
 * Description
 */

public abstract class CommonRefreshRecyclerView extends SmartRefreshLayout {

    private BaseDataBindingAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int mPage;

    public CommonRefreshRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mRecyclerView);
    }

    public CommonRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CommonRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void getData(boolean refresh) {
        mPage = refresh ? mPage = 1 : mPage + 1;
    }

    public int getPage() {
        return mPage;
    }

    public void init(Context context, int mTopPadding, int mSpacing, BaseDataBindingAdapter adapter) {

        mRecyclerView.setPadding(0, PixelUtils.dp2px(mTopPadding), 0, 0);
        mRecyclerView.setClipToPadding(false);

        setEnableLoadMore(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemBottomDecoration(mSpacing));
        mAdapter = adapter;
        mAdapter.setEmptyView(getEmptyView());
        mAdapter.isUseEmpty(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 设置空视图
     */
    protected abstract View getEmptyView();

    public void onHttpSuccess(boolean hasMore, List data) {
        onHttpSuccess(mPage, hasMore, data);
    }

    public void onHttpSuccess(int page, boolean hasMore, List data) {
        setEnableLoadMore(hasMore);
        if (page == 1) {
            mAdapter.getData().clear();
        }
        mAdapter.getData().addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    public void onHttpComplete(boolean showEmpty) {
        finishRefresh();
        finishLoadMore();
        if (showEmpty) {
            mAdapter.isUseEmpty(true);
            mAdapter.notifyDataSetChanged();
        }
    }

}
