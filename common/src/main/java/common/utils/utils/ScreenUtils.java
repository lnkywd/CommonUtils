package common.utils.utils;

import android.util.DisplayMetrics;

import common.utils.LibsApplication;


/**
 * Created by wd on 2017/7/10.
 */

public class ScreenUtils {

    /**
     * 获取 显示信息
     */
    public static DisplayMetrics getDisplayMetrics() {
        return LibsApplication.getInstance().getResources().getDisplayMetrics();
    }

    public static int getWidth() {
        return getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        return getDisplayMetrics().heightPixels;
    }

}
