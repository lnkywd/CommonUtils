package common.utils.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

import java.util.List;

import common.utils.utils.PixelUtils;
import common.utils.utils.recycler.DividerItemBottomDecoration;

/**
 * @author wd
 * @date 2018/04/09
 * Email 18842602830@163.com
 * Description
 */

public class CommonRefreshRecyclerView extends SmartRefreshLayout {

    private BaseQuickAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int mPage = 1;
    private boolean hasMore = false;
    /**
     * 在倒数第几个 item 时进行数据加载，默认是倒数第 3 个
     */
    private int mLoadPosition = 3;
    /**
     * 是否开启快速加载，默认关闭
     */
    private boolean enableQuickLoad = false;

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

    public void setEnableQuickLoad(boolean enableQuickLoad) {
        this.enableQuickLoad = enableQuickLoad;
    }

    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    public void getData(boolean refresh) {
        mPage = refresh ? mPage = 1 : mPage + 1;
    }

    public int getPage() {
        return mPage;
    }

    public void init(Context context, int mTopPadding, int mSpacing, BaseQuickAdapter adapter) {

        mRecyclerView.setPadding(0, PixelUtils.dp2px(mTopPadding), 0, 0);
        mRecyclerView.setClipToPadding(false);

        setEnableLoadMore(false);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemBottomDecoration(mSpacing));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!enableQuickLoad) {
                    return;
                }
                int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int calPosition = linearLayoutManager.getItemCount() - 1 - lastItemPosition;
                if (calPosition <= mLoadPosition && calPosition > 0) {
                    autoLoadMore();
                }

            }
        });
        mAdapter = adapter;
        if (getEmptyView() != null) {
            mAdapter.setEmptyView(getEmptyView());
        }
        mAdapter.isUseEmpty(false);
        mRecyclerView.setAdapter(mAdapter);
        setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int headerHeight, int extendHeight) {

            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {

            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int extendHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
                if (newState == RefreshState.None) {
                    setEnableLoadMore(hasMore);
                }
            }
        });
    }

    /**
     * 设置空视图
     */
    protected View getEmptyView() {
        return null;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void onHttpSuccess(boolean hasMore, List data) {
        onHttpSuccess(mPage, hasMore, data);
    }

    public void onHttpSuccess(int page, boolean hasMore, List data) {
        if (page == 1) {
            mAdapter.getData().clear();
            setEnableLoadMore(hasMore);
        }
        this.hasMore = hasMore;
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
