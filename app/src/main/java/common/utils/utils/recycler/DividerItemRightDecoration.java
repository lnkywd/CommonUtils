package common.utils.utils.recycler;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import common.utils.utils.PixelUtils;


/**
 * @author wd
 * @date 2018/03/21
 * Email 18842602830@163.com
 * Description
 */

public class DividerItemRightDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public DividerItemRightDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, PixelUtils.dp2px(spacing), 0);

    }
}
