package common.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;

import common.utils.R;

/**
 * @author wd
 * @date 2018/09/19
 * Email 18842602830@163.com
 * Description 验证码输入 View
 * https://github.com/EoniJJ/PasswordView/blob/master/passwordview/src/main/java/com/arron/passwordview/PasswordView.java
 * <p>
 * passwordLength (密码长度)
 * mode (样式切换 underline->下划线 rect->边框)
 * passwordPadding (每个密码框间隔)
 * borderColor （边框颜色）
 * borderWidth（边框宽度）
 * cursorFlashTime（光标闪烁间隔时间）
 * isCursorEnable（是否启用光标）
 * cipherTextSize（‘*’号大小）
 * cursorColor（光标颜色）
 * cipherEnable(是否启用‘*’显示)
 * currentBorderColor(当前位置的边框颜色)
 * passwordColor（密码颜色）
 * borderDistance(边框距离文字距离)
 * borderLength(边框长度)
 */

public class CodeInputView extends View {

    private static String CIPHER_TEXT = "*"; //密文符号
    private Mode mode; //样式模式
    private int passwordLength;//密码个数
    private long cursorFlashTime;//光标闪动间隔时间
    private int passwordPadding;//每个密码间的间隔
    private int passwordSize;//单个密码大小
    private int borderColor;//边框颜色
    private int borderWidth;//下划线粗细
    private int cursorPosition;//光标位置
    private int cursorWidth;//光标粗细
    private int cursorColor;//光标颜色
    private int passwordColor;// 密码颜色
    private int borderDistance;// 边框距离文字距离
    private int borderLength;// 边框长度
    private boolean isCursorShowing;//光标是否正在显示
    private boolean isCursorEnable;//是否开启光标
    private boolean isInputComplete;//是否输入完毕
    private boolean cipherEnable;//是否开启密文
    private int currentBorderColor;// 当前位置的边框颜色
    private String[] password;//密码数组
    private InputMethodManager inputManager;
    private PasswordListener passwordListener;
    private Paint paint;
    private Timer timer;
    private TimerTask timerTask;

    public CodeInputView(Context context) {
        super(context);
    }

    /**
     * 当前只支持从xml中构建该控件
     *
     * @param context
     * @param attrs
     */
    public CodeInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttribute(attrs);
    }

    private void readAttribute(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.wd_CodeInputView);
            mode = Mode.formMode(typedArray.getInteger(R.styleable.wd_CodeInputView_mode, Mode.UNDERLINE.getMode()));
            passwordLength = typedArray.getInteger(R.styleable.wd_CodeInputView_passwordLength, 4);
            cursorFlashTime = typedArray.getInteger(R.styleable.wd_CodeInputView_cursorFlashTime, 500);
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_borderWidth, dp2px(3));
            passwordSize = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_passwordSize, dp2px(20));
            borderDistance = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_borderDistance, 0);
            borderLength = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_borderLength, 0);
            borderColor = typedArray.getColor(R.styleable.wd_CodeInputView_borderColor, Color.BLACK);
            cursorColor = typedArray.getColor(R.styleable.wd_CodeInputView_cursorColor, Color.GRAY);
            isCursorEnable = typedArray.getBoolean(R.styleable.wd_CodeInputView_isCursorEnable, true);
            currentBorderColor = typedArray.getColor(R.styleable.wd_CodeInputView_currentBorderColor, Color.BLUE);
            passwordColor = typedArray.getColor(R.styleable.wd_CodeInputView_passwordColor, Color.GRAY);
            //如果为边框样式，则padding 默认置为0
            if (mode == Mode.UNDERLINE) {
                passwordPadding = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_passwordPadding, dp2px(15));
            } else {
                passwordPadding = typedArray.getDimensionPixelSize(R.styleable.wd_CodeInputView_passwordPadding, 0);
            }
            cipherEnable = typedArray.getBoolean(R.styleable.wd_CodeInputView_cipherEnable, false);
            typedArray.recycle();
        }
        password = new String[passwordLength];
        init();
    }

    private int dp2px(float dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void init() {
        setFocusableInTouchMode(true);
        MyKeyListener MyKeyListener = new MyKeyListener();
        setOnKeyListener(MyKeyListener);
        inputManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        paint = new Paint();
        paint.setAntiAlias(true);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                isCursorShowing = !isCursorShowing;
                postInvalidate();
            }
        };
        timer = new Timer();
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        postInvalidate();
    }

    /**
     * 删除
     */
    private String delete() {
        String deleteText = null;
        if (cursorPosition > 0) {
            deleteText = password[cursorPosition - 1];
            password[cursorPosition - 1] = null;
            cursorPosition--;
        } else if (cursorPosition == 0) {
            deleteText = password[cursorPosition];
            password[cursorPosition] = null;
        }
        isInputComplete = false;
        return deleteText;
    }

    /**
     * 增加
     */
    private String add(String c) {
        String addText = null;
        if (cursorPosition < passwordLength) {
            addText = c;
            password[cursorPosition] = c;
            cursorPosition++;
            if (cursorPosition == passwordLength) {
                isInputComplete = true;
                if (passwordListener != null) {
                    passwordListener.passwordComplete();
                }
            }
        }
        return addText;
    }

    /**
     * 获取密码
     */
    private String getPassword() {
        StringBuffer stringBuffer = new StringBuffer();
        for (String c : password) {
            if (TextUtils.isEmpty(c)) {
                continue;
            }
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            inputManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
        }
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER; //输入类型为数字
        return super.onCreateInputConnection(outAttrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /**
             * 弹出软键盘
             */
            requestFocus();
            inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED);
            return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //光标宽度
        cursorWidth = dp2px(2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mode == Mode.UNDERLINE) {
            //绘制下划线
            drawUnderLine(canvas, paint);
        } else {
            //绘制方框
            drawRect(canvas, paint);
        }
        //绘制光标
        drawCursor(canvas, paint);
        //绘制密码文本
        drawCipherText(canvas, paint);
    }

    /**
     * 绘制密码框下划线
     *
     * @param canvas
     * @param paint
     */
    private void drawUnderLine(Canvas canvas, Paint paint) {
        //画笔初始化
        paint.setStrokeWidth(borderWidth);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < passwordLength; i++) {
            paint.setColor(i == cursorPosition ? currentBorderColor : borderColor);
            //根据密码位数for循环绘制直线
            // 起始点x为paddingLeft + (单个密码框大小 + 密码框边距) * i , 起始点y为paddingTop + 单个密码框大小
            // 终止点x为 起始点x + 单个密码框大小 , 终止点y与起始点一样不变
            canvas.drawLine(getPaddingLeft() + (borderLength + passwordPadding) * i, getPaddingTop() + passwordSize + borderDistance * 2,
                    getPaddingLeft() + (borderLength + passwordPadding) * i + borderLength, getPaddingTop() + passwordSize + borderDistance * 2,
                    paint);
        }
    }

    private void drawRect(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.STROKE);
        Rect rect;
        for (int i = 0; i < passwordLength; i++) {
            paint.setColor(i == cursorPosition ? currentBorderColor : borderColor);
            int startX = getPaddingLeft() + (borderLength + passwordPadding) * i;
            int startY = getPaddingTop();
            int stopX = getPaddingLeft() + (borderLength + passwordPadding) * i + borderLength;
            int stopY = getPaddingTop() + passwordSize + borderDistance * 2;
            rect = new Rect(startX, startY, stopX, stopY);
            canvas.drawRect(rect, paint);
        }
    }

    /**
     * 绘制光标
     *
     * @param canvas
     * @param paint
     */
    private void drawCursor(Canvas canvas, Paint paint) {
        //画笔初始化
        paint.setColor(cursorColor);
        paint.setStrokeWidth(cursorWidth);
        paint.setStyle(Paint.Style.FILL);
        //光标未显示 && 开启光标 && 输入位数未满 && 获得焦点
        if (!isCursorShowing && isCursorEnable && !isInputComplete && hasFocus()) {
            // 起始点x = paddingLeft + 单个密码框大小 / 2 + (单个密码框大小 + 密码框间距) * 光标下标
            // 起始点y = paddingTop + (单个密码框大小 - 光标大小) / 2
            // 终止点x = 起始点x
            // 终止点y = 起始点y + 光标高度
            canvas.drawLine((getPaddingLeft() + borderLength / 2) + (borderLength + passwordPadding) * cursorPosition,
                    getPaddingTop() + borderDistance,
                    (getPaddingLeft() + borderLength / 2) + (borderLength + passwordPadding) * cursorPosition,
                    getPaddingTop() + passwordSize + borderDistance,
                    paint);
        }
    }

    /**
     * 绘制密码替代符号
     *
     * @param canvas
     * @param paint
     */
    private void drawCipherText(Canvas canvas, Paint paint) {
        //画笔初始化
        paint.setColor(passwordColor);
        paint.setTextSize(passwordSize);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.FILL);
        //文字居中的处理
        Rect r = new Rect();
        canvas.getClipBounds(r);
        int cHeight = r.height();
        //根据输入的密码位数，进行for循环绘制
        for (int i = 0; i < password.length; i++) {
            if (!TextUtils.isEmpty(password[i])) {
                // x = paddingLeft + 单个密码框大小/2 + ( 密码框大小 + 密码框间距 ) * i
                // y = paddingTop + 文字居中所需偏移量
                paint.getTextBounds(cipherEnable ? CIPHER_TEXT : password[i], 0, CIPHER_TEXT.length(), r);
                float y = cHeight / 2f + r.height() / 2f - r.bottom;
                if (cipherEnable) {
                    //没有开启明文显示，绘制密码密文
                    canvas.drawText(CIPHER_TEXT,
                            (getPaddingLeft() + borderLength / 2) + (borderLength + passwordPadding) * i,
                            getPaddingTop() + y, paint);
                } else {
                    //明文显示，直接绘制密码
                    canvas.drawText(password[i],
                            (getPaddingLeft() + borderLength / 2) + (borderLength + passwordPadding) * i,
                            getPaddingTop() + y, paint);
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //cursorFlashTime为光标闪动的间隔时间
        timer.scheduleAtFixedRate(timerTask, 0, cursorFlashTime);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        timer.cancel();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superState", super.onSaveInstanceState());
        bundle.putStringArray("password", password);
        bundle.putInt("cursorPosition", cursorPosition);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            password = bundle.getStringArray("password");
            cursorPosition = bundle.getInt("cursorPosition");
            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = 0;
        switch (widthMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                if (borderLength == 0) {
                    borderLength = passwordSize;
                }
                //没有指定大小，宽度 = 单个密码框大小 * 密码位数 + 密码框间距 *（密码位数 - 1）
                width = borderLength * passwordLength + passwordPadding * (passwordLength - 1);
                break;
            case MeasureSpec.EXACTLY:
                //指定大小，宽度 = 指定的大小
                width = MeasureSpec.getSize(widthMeasureSpec);
                //密码框大小等于 (宽度 - 密码框间距 *(密码位数 - 1)) / 密码位数
                borderLength = (width - (passwordPadding * (passwordLength - 1))) / passwordLength;
                break;
            default:
        }
        setMeasuredDimension(width, passwordSize + borderDistance * 2);

    }

    private int sp2px(float spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public void setPasswordListener(PasswordListener passwordListener) {
        this.passwordListener = passwordListener;
    }

    public void setPasswordSize(int passwordSize) {
        this.passwordSize = passwordSize;
        postInvalidate();
    }

    public void setPasswordLength(int passwordLength) {
        this.passwordLength = passwordLength;
        postInvalidate();
    }

    public void setCursorColor(int cursorColor) {
        this.cursorColor = cursorColor;
        postInvalidate();
    }

    public void setCursorEnable(boolean cursorEnable) {
        isCursorEnable = cursorEnable;
        postInvalidate();
    }

    public void setCipherEnable(boolean cipherEnable) {
        this.cipherEnable = cipherEnable;
        postInvalidate();
    }

    public enum Mode {
        /**
         * 下划线样式
         */
        UNDERLINE(0),

        /**
         * 边框样式
         */
        RECT(1);
        private int mode;

        Mode(int mode) {
            this.mode = mode;
        }

        static Mode formMode(int mode) {
            for (Mode m : values()) {
                if (mode == m.mode) {
                    return m;
                }
            }
            throw new IllegalArgumentException();
        }

        public int getMode() {
            return this.mode;
        }
    }

    /**
     * 密码监听者
     */
    public interface PasswordListener {
        /**
         * 输入/删除监听
         *
         * @param changeText 输入/删除的字符
         */
        void passwordChange(String changeText);

        /**
         * 输入完成
         */
        void passwordComplete();

        /**
         * 确认键后的回调
         *
         * @param password   密码
         * @param isComplete 是否达到要求位数
         */
        void keyEnterPress(String password, boolean isComplete);

    }

    class MyKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            int action = event.getAction();
            if (action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    /**
                     * 删除操作
                     */
                    if (TextUtils.isEmpty(password[0])) {
                        return true;
                    }
                    String deleteText = delete();
                    if (passwordListener != null && !TextUtils.isEmpty(deleteText)) {
                        passwordListener.passwordChange(deleteText);
                    }
                    postInvalidate();
                    return true;
                }
                if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    /**
                     * 只支持数字
                     */
                    if (isInputComplete) {
                        return true;
                    }
                    String addText = add((keyCode - 7) + "");
                    if (passwordListener != null && !TextUtils.isEmpty(addText)) {
                        passwordListener.passwordChange(addText);
                    }
                    postInvalidate();
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    /**
                     * 确认键
                     */
                    if (passwordListener != null) {
                        passwordListener.keyEnterPress(getPassword(), isInputComplete);
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
