package common.utils.view.dialogandpop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import common.utils.utils.ActivityUtils;
import common.utils.utils.ScreenUtils;

/**
 * @author wd
 * @date 2018/04/27
 * Email 18842602830@163.com
 * Description
 */

public class CommonPopWindow extends PopupWindow {

    private Context mContext;
    private OnCustomDismissListener mOnCustomDismissListener;

    public CommonPopWindow(View contentView) {
        super(contentView);
        mContext = contentView.getContext();
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        setAnimationStyle(android.R.style.Animation_Dialog);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        setBackgroundDrawable(dw);
        setFocusable(true);
        setOutsideTouchable(true);
        setTouchable(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                ScreenUtils.backgroundAlpha((Activity) mContext, 1.0f);
                if (mOnCustomDismissListener != null) {
                    mOnCustomDismissListener.onDismiss();
                }
            }
        });
    }

    public void setOnCustomDismissListener(OnCustomDismissListener mOnCustomDismissListener) {
        this.mOnCustomDismissListener = mOnCustomDismissListener;
    }

    /**
     * 显示
     */
    public void showPopup() {
        showPopup(Gravity.CENTER, 0, 0, 0.7f);
    }

    public void showPopup(int gravity, int x, int y, float alpha) {
        if (!isShowing()) {
            showAtLocation(ActivityUtils.getTopActivity().getWindow().getDecorView(), gravity, x, y);
            ScreenUtils.backgroundAlpha((Activity) mContext, alpha);//0.0-1.0
        }
    }

    public void showPopup(int gravity) {
        showPopup(gravity, 0, 0, 0.7f);
    }

    public void showPopup(int gravity, int x) {
        showPopup(gravity, x, 0, 0.7f);
    }

    public void showPopup(int gravity, int x, int y) {
        showPopup(gravity, x, y, 0.7f);
    }

    public interface OnCustomDismissListener {
        void onDismiss();
    }

}
