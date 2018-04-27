package common.utils.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import common.utils.LibsApplication;


/**
 * Created by wd on 2017/7/10.
 */

public class ScreenUtils {

    public static int getWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 获取 显示信息
     */
    public static DisplayMetrics getDisplayMetrics() {
        return LibsApplication.getInstance().getResources().getDisplayMetrics();
    }

    public static int getHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}
