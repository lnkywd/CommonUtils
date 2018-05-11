package common.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @author wd
 * @date 2018/04/28
 * Email 18842602830@163.com
 * Description
 * https://mp.weixin.qq.com/s/BHbmtfUzg3XTaGrGcX5-bg
 * <p>
 * mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
 * @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
 * super.onScrolled(recyclerView, dx, dy);
 * int fPos = mLinearLayoutManager.findFirstVisibleItemPosition();
 * int lPos = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
 * for (int i = fPos; i <= lPos; i++) {
 * View view = mLinearLayoutManager.findViewByPosition(i);
 * AdImageViewVersion1 adImageView = view.findViewById(R.id.id_iv_ad);
 * if (adImageView.getVisibility() == View.VISIBLE) {
 * adImageView.setDy(mLinearLayoutManager.getHeight() - view.getTop());
 * }
 * }
 * }
 * });
 */

public class AdImageView extends AppCompatImageView {

    private int mDx;
    private int mMinDx;

    public AdImageView(Context context) {
        super(context);
    }

    public AdImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMinDx = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        int w = getWidth();
        int h = (int) (getWidth() * 1.0f / drawable.getIntrinsicWidth() * drawable.getIntrinsicHeight());
        drawable.setBounds(0, 0, w, h);
        canvas.save();
        canvas.translate(0, -getDx());
        super.onDraw(canvas);
        canvas.restore();
    }

    public int getDx() {
        return mDx;
    }

    public void setDx(int dx) {
        if (getDrawable() == null) {
            return;
        }
        mDx = dx - mMinDx;
        if (mDx <= 0) {
            mDx = 0;
        }
        if (mDx > getDrawable().getBounds().height() - mMinDx) {
            mDx = getDrawable().getBounds().height() - mMinDx;
        }
        invalidate();
    }
}