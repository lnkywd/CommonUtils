package common.utils.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author wd
 * @date 2018/08/06
 * Email 18842602830@163.com
 * Description
 */

public class NumberUtils {

    public static String formatDoubleX2(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(d);
    }

    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("0.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(d);
    }

    public static String formatFloat(float d) {
        DecimalFormat df = new DecimalFormat("0.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(d);
    }

    public static String formatFloatX2(float d) {
        DecimalFormat df = new DecimalFormat("0.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(d);
    }

}
