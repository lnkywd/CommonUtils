package common.utils.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import common.utils.LibsApplication;


public class ToastUtils {

    private static Handler mHandler = null;
    private static Toast toast = null;

    public static void showShort(int resId) {
        showShort(null, resId);
    }

    public static void showShort(Context context, int resId) {
        context = (context == null ? LibsApplication.getInstance() : context);
        showShort(context, context.getString(resId));
    }

    public static void showShort(String text) {
        showShort(null, text);
    }

    public static void showShort(Context context, final String text) {
        context = (context == null ? LibsApplication.getInstance() : context);
        if (!Utils.isAppOnForeground(context)) {
            return;
        }
        sharedHandler(context).post(new Runnable() {
            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(text);
                    toast.setDuration(Toast.LENGTH_SHORT);
                } else {
                    toast = Toast.makeText(LibsApplication.getInstance(), text, Toast.LENGTH_SHORT);
                }
                toast.show();
            }
        });
    }

    public static void showLong(int resId) {
        showLong(null, resId);
    }

    public static void showLong(Context context, int resId) {
        context = (context == null ? LibsApplication.getInstance() : context);
        showLong(context, context.getString(resId));
    }

    public static void showLong(String text) {
        showLong(null, text);
    }

    public static void showLong(Context context, final String text) {
        context = (context == null ? LibsApplication.getInstance() : context);
        if (!Utils.isAppOnForeground(context)) {
            return;
        }
        sharedHandler(context).post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(text);
                    toast.setDuration(Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(LibsApplication.getInstance(), text, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        });
    }

    private static Handler sharedHandler(Context context) {
        if (context == null) {
            context = LibsApplication.getInstance();
        }
        if (mHandler == null) {
            mHandler = Handlers.sharedHandler(context);
        }

        return mHandler;
    }
}
