package common.utils.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import java.lang.reflect.Field;

import common.utils.R;
import common.utils.utils.PixelUtils;

/**
 * @author wd
 * @date 2018/09/03
 * Email 18842602830@163.com
 * Description
 */

public class CommonXEditText extends AppCompatEditText {

    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 删除图标 资源ID
     */
    private int icDeleteResID;
    /**
     * 删除图标
     */
    private Drawable icDelete;
    /**
     * 删除图标起点(x,y)、删除图标宽、高（px）
     */
    private int deleteX, deleteY, deleteWidth, deleteHeight;
    /**
     * 左侧图标 资源ID（点击 & 无点击）
     */
    private int icLeftClickResID, icLeftUnClickResID;
    /**
     * 左侧图标（点击 & 未点击）
     */
    private Drawable icLeftClick, icLeftUnclick;
    /**
     * 左侧图标起点（x,y）、左侧图标宽、高（px）
     */
    private int leftX, leftY, leftWidth, leftHeight;

    /**
     * 分割线变量
     * <p>
     * 点击时 & 未点击颜色
     */
    private int lineColorClick, lineColorUnClick;
    private int color;
    private int linePosition;
    private boolean isShowLine = false;


    public CommonXEditText(Context context) {
        super(context);

    }

    public CommonXEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 步骤1：初始化属性
     */

    private void init(Context context, AttributeSet attrs) {

        // 获取控件资源
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.wd_CommonXEditText);

        // 初始化左侧图标（点击 & 未点击）

        // a. 点击状态的左侧图标
        // 1. 获取资源ID
        icLeftClickResID = typedArray.getResourceId(R.styleable.wd_CommonXEditText_ic_left_click, 0);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        if (icLeftClickResID != 0) {
            icLeftClick = ContextCompat.getDrawable(context, icLeftClickResID);
            // 3. 设置图标大小
            // 起点(x，y)、宽= leftWidth、高 = leftHeight
            leftX = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_left_x, 0);
            leftY = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_left_y, 0);
            leftWidth = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_left_width, PixelUtils.dp2px(20));
            leftHeight = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_left_height, PixelUtils.dp2px(20));

            icLeftClick.setBounds(leftX, leftY, leftWidth, leftHeight);
        }
        // Drawable.setBounds(x,y,width,height) = 设置Drawable的初始位置、宽和高等信息
        // x = 组件在容器X轴上的起点、y = 组件在容器Y轴上的起点、width=组件的长度、height = 组件的高度

        // b. 未点击状态的左侧图标
        // 1. 获取资源ID
        icLeftUnClickResID = typedArray.getResourceId(R.styleable.wd_CommonXEditText_ic_left_unclick, 0);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        // 3. 设置图标大小（此处默认左侧图标点解 & 未点击状态的大小相同）
        if (icLeftUnClickResID != 0) {
            icLeftUnclick = ContextCompat.getDrawable(context, icLeftUnClickResID);
            if (icLeftUnclick != null) {
                icLeftUnclick.setBounds(leftX, leftY, leftWidth, leftHeight);
            }
        }


        // 初始化删除图标

        // 1. 获取资源ID
        icDeleteResID = typedArray.getResourceId(R.styleable.wd_CommonXEditText_ic_delete, R.mipmap.wd_icon_delete);
        // 2. 根据资源ID获取图标资源（转化成Drawable对象）
        icDelete = ContextCompat.getDrawable(context, getDeleteRes());
        if (getDeleteDrawable() != null) {
            icDelete = getDeleteDrawable();
        }
        // 3. 设置图标大小
        // 起点(x，y)、宽= leftWidth、高 = leftHeight
        deleteX = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_delete_x, 0);
        deleteY = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_delete_y, 0);
        deleteWidth = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_delete_width, PixelUtils.dp2px(20));
        deleteHeight = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_delete_height, PixelUtils.dp2px(20));
        icDelete.setBounds(deleteX, deleteY, getDeleteWidth(), getDeleteHeight());

        // 设置EditText左侧 & 右侧的图片（初始状态仅有左侧图片））

        setCompoundDrawables(icLeftUnclick, null,
                null, null);

        // setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)介绍
        // 作用：在EditText上、下、左、右设置图标（相当于android:drawableLeft=""  android:drawableRight=""）
        // 备注：传入的Drawable对象必须已经setBounds(x,y,width,height)，即必须设置过初始位置、宽和高等信息
        // x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点 width:组件的长度 height:组件的高度
        // 若不想在某个地方显示，则设置为null

        // 另外一个相似的方法：setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)
        // 作用：在EditText上、下、左、右设置图标
        // 与setCompoundDrawables的区别：setCompoundDrawablesWithIntrinsicBounds（）传入的Drawable的宽高=固有宽高（自动通过getIntrinsicWidth（）& getIntrinsicHeight（）获取）
        // 不需要设置setBounds(x,y,width,height)

        // 初始化光标（颜色 & 粗细）

        // 原理：通过 反射机制 动态设置光标
        // 1. 获取资源ID
        if (getCursor() != 0) {
            int cursor = typedArray.getResourceId(R.styleable.wd_CommonXEditText_cursor, R.drawable.wd_common_x_edittext_cursor);
            cursor = getCursor();
            try {

                // 2. 通过反射 获取光标属性
                Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
                f.setAccessible(true);
                // 3. 传入资源ID
                f.set(this, cursor);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // 初始化分割线（颜色、粗细、位置）

        // 1. 设置画笔
        mPaint = new Paint();
        // 分割线粗细
        mPaint.setStrokeWidth(2.0f);

        // 2. 设置分割线颜色（使用十六进制代码，如#333、#8e8e8e）
        isShowLine = typedArray.getBoolean(R.styleable.wd_CommonXEditText_show_line, false);
        // 默认 = 蓝色#1296db
        int lineColorClickDefault = ContextCompat.getColor(context, R.color.lineColor_click);
        // 默认 = 灰色#9b9b9b
        int lineColorunClickDefault = ContextCompat.getColor(context, R.color.lineColor_unclick);
        lineColorClick = typedArray.getColor(R.styleable.wd_CommonXEditText_lineColor_click, lineColorClickDefault);
        lineColorUnClick = typedArray.getColor(R.styleable.wd_CommonXEditText_lineColor_unclick, lineColorunClickDefault);
        color = lineColorUnClick;
        // 分割线默认颜色 = 灰色
        mPaint.setColor(lineColorUnClick);
        // 字体默认颜色 = 灰色
//        setTextColor(color);

        // 3. 分割线位置
        linePosition = (int) typedArray.getDimension(R.styleable.wd_CommonXEditText_linePosition, 1);
        // 消除自带下划线
        setBackground(null);

        typedArray.recycle();

    }

    /**
     * 设置删除图标资源
     */
    protected int getDeleteRes() {
        return this.icDeleteResID;
    }

    /**
     * 设置删除图标 Drawable
     */
    protected Drawable getDeleteDrawable() {
        return null;
    }

    protected int getDeleteWidth() {
        return this.deleteWidth;
    }

    protected int getDeleteHeight() {
        return this.deleteHeight;
    }

    protected int getCursor() {
        return 0;
    }

    public CommonXEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 作用：绘制分割线
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(color);
//        setTextColor(color);
        if (isShowLine) {
            // 绘制分割线
            // 需要考虑：当输入长度超过输入框时，所画的线需要跟随着延伸
            // 解决方案：线的长度 = 控件长度 + 延伸后的长度

            // 获取延伸后的长度
            int x = this.getScrollX();
            // 获取控件长度
            int w = this.getMeasuredWidth();

            // 传入参数时，线的长度 = 控件长度 + 延伸后的长度
            canvas.drawLine(0, this.getMeasuredHeight() - linePosition, w + x,
                    this.getMeasuredHeight() - linePosition, mPaint);
        }
    }

    /**
     * 复写EditText本身的方法：onTextChanged（）
     * 调用时刻：当输入框内容变化时
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setDeleteIconVisible(hasFocus() && text.length() > 0, hasFocus());
        // hasFocus()返回是否获得EditTEXT的焦点，即是否选中
        // setDeleteIconVisible（） = 根据传入的是否选中 & 是否有输入来判断是否显示删除图标->>关注1
    }

    /**
     * 复写EditText本身的方法：onFocusChanged（）
     * 调用时刻：焦点发生变化时
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setDeleteIconVisible(focused && length() > 0, focused);
        // focused = 是否获得焦点
        // 同样根据setDeleteIconVisible（）判断是否要显示删除图标->>关注1
    }

    /**
     * 作用：对删除图标区域设置为"点击 即 清空搜索框内容"
     * 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 原理：当手指抬起的位置在删除图标的区域，即视为点击了删除图标 = 清空搜索框内容
        switch (event.getAction()) {
            // 判断动作 = 手指抬起时
            case MotionEvent.ACTION_UP:
                Drawable drawable = icDelete;

                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {

                    // 判断条件说明
                    // event.getX() ：抬起时的位置坐标
                    // getWidth()：控件的宽度
                    // getPaddingRight():删除图标图标右边缘至EditText控件右边缘的距离
                    // 即：getWidth() - getPaddingRight() = 删除图标的右边缘坐标 = X1
                    // getWidth() - getPaddingRight() - drawable.getBounds().width() = 删除图标左边缘的坐标 = X2
                    // 所以X1与X2之间的区域 = 删除图标的区域
                    // 当手指抬起的位置在删除图标的区域（X2=<event.getX() <=X1），即视为点击了删除图标 = 清空搜索框内容
                    setText("");

                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }

    /**
     * 关注1
     * 作用：判断是否显示删除图标 & 设置分割线颜色
     */
    private void setDeleteIconVisible(boolean deleteVisible, boolean leftVisible) {
        setCompoundDrawables(leftVisible ? icLeftClick : icLeftUnclick, null,
                deleteVisible ? icDelete : null, null);
        color = leftVisible ? lineColorClick : lineColorUnClick;
//        setTextColor(color);
        invalidate();
    }

}