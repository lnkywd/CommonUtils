package common.utils.utils.recycler;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author wd
 * @date 2018/03/31
 * Email 18842602830@163.com
 * Description
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;
    private boolean includeEdge;

    private int mTopViewCount = 0;
    private boolean addBottom = false;

    public GridSpacingItemDecoration(int spacing, boolean includeEdge) {
        this(spacing, includeEdge, 0);
    }

    public GridSpacingItemDecoration(int space, boolean includeEdge, int topCount) {
        this(space, includeEdge, topCount, false);
    }

    public GridSpacingItemDecoration(int space, boolean includeEdge, int topCount, boolean addBottom) {
        this.spacing = space;
        this.includeEdge = includeEdge;
        mTopViewCount = topCount;
        this.addBottom = addBottom;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int count = layoutManager.getSpanCount();

        int position = parent.getChildAdapterPosition(view) - mTopViewCount;
        int total = parent.getChildCount() - mTopViewCount;
        if (position < 0) {
            return;
        }

        int column = position % count; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / count; // spacing - column * ((1f / count) * spacing)
            outRect.right = (column + 1) * spacing / count; // (column + 1) * ((1f / count) * spacing)

            if (position < count) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / count; // column * ((1f / count) * spacing)
            outRect.right = spacing - (column + 1) * spacing / count; // spacing - (column + 1) * ((1f /    count) * spacing)
            if (position >= count) {
                outRect.top = spacing; // item top
            }
        }

        if (addBottom && total - position <= count) {
            outRect.bottom = spacing;
        }
    }
}
