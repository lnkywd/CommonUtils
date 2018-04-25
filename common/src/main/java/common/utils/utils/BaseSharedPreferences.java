package common.utils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wd
 * @date 2018/04/25
 * Email 18842602830@163.com
 * Description
 */

public class BaseSharedPreferences {

    private static SharedPreferences getSharedPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return false;
        }
        return spf.contains(key);
    }

    // [start]******************int类型****************

    private static SharedPreferences.Editor setBasePrefInt(Context context, String key, int value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return null;
        }
        SharedPreferences.Editor edit = spf.edit();
        edit.putInt(key, value);
        return edit;
    }

    public static boolean setPrefInt(Context context, String key, int value) {
        boolean flag = false;
        SharedPreferences.Editor edit = setBasePrefInt(context, key, value);
        if (edit == null) {
            return false;
        }
        synchronized (edit) {
            flag = edit.commit();
        }
        return flag;
    }

    public static void setPrefIntApply(Context context, String key, int value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.putInt(key, value);
            edit.apply();
        }
    }

    public static int getPrefInt(Context context, String key, int defaultValue) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null && spf.contains(key)) {
            return spf.getInt(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public static int getPrefInt(Context context, String key) {
        return getPrefInt(context, key, 0);
    }

    // [end]******************************************

    // [start]******************long类型****************

    private static SharedPreferences.Editor setBasePrefLong(Context context, String key, long value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return null;
        }
        SharedPreferences.Editor edit = spf.edit();
        edit.putLong(key, value);
        return edit;
    }

    public static boolean setPrefLong(Context context, String key, long value) {
        boolean flag = false;
        SharedPreferences.Editor edit = setBasePrefLong(context, key, value);
        if (edit == null) {
            return false;
        }
        synchronized (edit) {
            flag = edit.commit();
        }
        return flag;
    }

    public static void setPrefLongApply(Context context, String key, long value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.putLong(key, value);
            edit.apply();
        }
    }

    public static long getPrefLong(Context context, String key, long defaultValue) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null && spf.contains(key)) {
            return spf.getLong(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public static long getPrefLong(Context context, String key) {
        return getPrefLong(context, key, 0);
    }

    // [end]******************************************

    // [start]*******************String类型************

    private static SharedPreferences.Editor setBasePrefString(Context context, String key, String value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return null;
        }
        SharedPreferences.Editor edit = spf.edit();
        edit.putString(key, value);
        return edit;
    }

    public static boolean setPrefString(Context context, String key, String value) {
        boolean flag = false;
        SharedPreferences.Editor edit = setBasePrefString(context, key, value);
        if (edit == null) {
            return false;
        }
        synchronized (edit) {
            flag = edit.commit();
        }
        return flag;
    }

    public static void setPrefStringApply(Context context, String key, String value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.putString(key, value);
            edit.apply();
        }
    }

    public static String getPrefString(Context context, String key, String defaultValue) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null && spf.contains(key)) {
            return spf.getString(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public static String getPrefString(Context context, String key) {
        return getPrefString(context, key, "");
    }

    // [end]******************************************

    // [start]****************boolean类型**************

    private static SharedPreferences.Editor setBasePrefBoolean(Context context, String key, boolean value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return null;
        }
        SharedPreferences.Editor edit = spf.edit();
        edit.putBoolean(key, value);
        return edit;
    }

    public static boolean setPrefBoolean(Context context, String key, boolean value) {
        boolean flag = false;
        SharedPreferences.Editor edit = setBasePrefBoolean(context, key, value);
        if (edit == null) {
            return false;
        }
        synchronized (edit) {
            flag = edit.commit();
        }
        return flag;
    }

    public static void setPrefBooleanApply(Context context, String key, boolean value) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.putBoolean(key, value);
            edit.apply();
        }
    }

    public static boolean getPrefBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null && spf.contains(key)) {
            return spf.getBoolean(key, defaultValue);
        } else {
            return defaultValue;
        }
    }

    public static boolean getPrefBoolean(Context context, String key) {
        return getPrefBoolean(context, key, false);
    }

    // [end]******************************************

    // [start]****************Set<String>类型**************

    private static SharedPreferences.Editor setBasePrefValues(Context context, String key, Set<String> values) {
        SharedPreferences spf = getSharedPref(context);
        if (spf == null) {
            return null;
        }
        SharedPreferences.Editor edit = spf.edit();
        edit.putStringSet(key, values);
        return edit;
    }

    public static boolean setPrefValues(Context context, String key, Set<String> values) {
        boolean flag = false;
        SharedPreferences.Editor edit = setBasePrefValues(context, key, values);
        if (edit == null) {
            return false;
        }
        synchronized (edit) {
            flag = edit.commit();
        }
        return flag;
    }

    public static void setPrefValuesApply(Context context, String key, Set<String> values) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null) {
            SharedPreferences.Editor edit = spf.edit();
            edit.putStringSet(key, values);
            edit.apply();
        }
    }

    public static Set<String> getPrefValues(Context context, String key, Set<String> defaultValues) {
        SharedPreferences spf = getSharedPref(context);
        if (spf != null && spf.contains(key)) {
            return spf.getStringSet(key, defaultValues);
        } else {
            return defaultValues;
        }
    }

    public static Set<String> getPrefValues(Context context, String key) {
        return getPrefValues(context, key, new HashSet<String>());
    }

    // [end]******************************************

}
