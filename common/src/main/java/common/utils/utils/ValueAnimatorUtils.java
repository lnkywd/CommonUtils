package common.utils.utils;

import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * @author wd
 * @date 2018/04/26
 * Email 18842602830@163.com
 * Description 属性动画帮助类
 */

public class ValueAnimatorUtils {

    /**
     * 如果动画被禁用，则重置动画缩放时长
     */
    public static void resetDurationScaleIfDisable() {
        if (getDurationScale() == 0) {
            resetDurationScale();
        }
    }

    private static float getDurationScale() {
        try {
            return getField().getFloat(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 重置动画缩放时长
     */
    public static void resetDurationScale() {
        try {
            getField().setFloat(null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private static Field getField() throws NoSuchFieldException {
        Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
        field.setAccessible(true);
        return field;
    }
}
