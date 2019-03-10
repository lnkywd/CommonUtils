package common.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import common.utils.R;

/**
 * @author wd
 * @date 2018/04/28
 * Email 18842602830@163.com
 * Description 没边距的 textview
 */

public class NoPaddingTextView extends AppCompatTextView {
    private Paint mPaint = getPaint();
    private Rect mBounds = new Rect();
    //是否去除字体内边距，true：去除 false：不去除
    private Boolean mRemoveFontPadding = false;

    public NoPaddingTextView(Context context) {
        super(context);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
    }

    /**
     * 初始化属性
     */
    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wd_NoPaddingTextView);
        mRemoveFontPadding = typedArray.getBoolean(R.styleable.wd_NoPaddingTextView_removeDefaultPadding, false);
        typedArray.recycle();
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRemoveFontPadding) {
            calculateTextParams();
            setMeasuredDimension(mBounds.right - mBounds.left, -mBounds.top + mBounds.bottom);
        }
    }

    /**
     * 计算文本参数
     */
    private String calculateTextParams() {
        String text = getText().toString();
        int textLength = text.length();
        mPaint.getTextBounds(text, 0, textLength, mBounds);
        if (textLength == 0) {
            mBounds.right = mBounds.left;
        }
        return text;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onDraw(Canvas canvas) {
        drawText(canvas);
    }

    /**
     * 绘制文本
     */
    private void drawText(Canvas canvas) {
        String text = calculateTextParams();
        int left = mBounds.left;
        int bottom = mBounds.bottom;
        mBounds.offset(-mBounds.left, -mBounds.top);
        mPaint.setAntiAlias(true);
        mPaint.setColor(getCurrentTextColor());
        canvas.drawText(text, (float) (-left), (float) (mBounds.bottom - bottom), mPaint);
    }
}