package common.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import common.utils.R;

/**
 * @author wd
 * @date 2018/05/09
 * Email 18842602830@163.com
 * Description
 */

public class MaxFrameLayout extends FrameLayout {

    private float mMaxWidth;
    private float mMaxHeight;

    public MaxFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public MaxFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wd_MaxFrameLayout, defStyleAttr, 0);
        mMaxWidth = typedArray.getDimension(R.styleable.wd_MaxFrameLayout_maxWidth, 0);
        mMaxHeight = typedArray.getDimension(R.styleable.wd_MaxFrameLayout_maxHeight, 0);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mMaxWidth > 0) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) mMaxWidth, MeasureSpec.AT_MOST);
        }
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) mMaxHeight, MeasureSpec.AT_MOST);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
