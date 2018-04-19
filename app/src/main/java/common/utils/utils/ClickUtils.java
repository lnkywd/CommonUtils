package common.utils.utils;

/**
 * @author wd
 * @date 2018/04/04
 * Email 18842602830@163.com
 * Description 点击事件工具类
 */

public class ClickUtils {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime = 0;

    /**
     * 返回 true ，说明是快速点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) > MIN_CLICK_DELAY_TIME) {
            flag = false;
            lastClickTime = curClickTime;
        }
        return flag;
    }

}
