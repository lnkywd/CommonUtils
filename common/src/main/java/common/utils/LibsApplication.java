package common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.liulishuo.filedownloader.FileDownloader;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import cn.dreamtobe.filedownloader.OkHttp3Connection;
import common.utils.utils.CrashUtils;
import common.utils.utils.LogUtils;
import common.utils.utils.Utils;

/**
 * Created by fzz on 2017/11/2 0002.
 */

public class LibsApplication extends Application {

    private static final String TAG = "LibsApplication";
    private static LibsApplication sInstance;

    private ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogUtils.d(TAG, "onActivityCreated() called with: activity = [" + activity + "], savedInstanceState = [" + savedInstanceState + "]");
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogUtils.d(TAG, "onActivityStarted() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUtils.d(TAG, "onActivityResumed() called with: activity = [" + activity + "]");
            MobclickAgent.onResume(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtils.d(TAG, "onActivityPaused() called with: activity = [" + activity + "]");
            MobclickAgent.onPause(activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogUtils.d(TAG, "onActivityStopped() called with: activity = [" + activity + "]");
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtils.d(TAG, "onActivitySaveInstanceState() called with: activity = [" + activity + "], outState = [" + outState + "]");
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtils.d(TAG, "onActivityDestroyed() called with: activity = [" + activity + "]");
        }
    };

    public static LibsApplication getInstance() {
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        boolean defaultProcess = shouldInit();
        if (defaultProcess) {
            sInstance = this;
            Utils.init(this);
            initLog();
            CrashUtils.init();
            registerActivityLifecycleCallbacks(mCallbacks);
            // 文件下载使用 okhttp
            FileDownloader.setupOnApplicationOnCreate(this)
                    .connectionCreator(new OkHttp3Connection.Creator())
                    .commit();
            initX5();
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public void initLog() {
        LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(isDebug())// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(isDebug())// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1);// log栈深度，默认为1
        LogUtils.d(config.toString());
    }

    private void initX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d(TAG, " onViewInitFinished is " + arg0);
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    protected boolean isDebug() {
        return false;
    }
}
