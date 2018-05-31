package common.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.alibaba.android.arouter.launcher.ARouter;

import common.utils.db.DbHelper;

/**
 * @author wd
 * @date 2018/04/26
 * Email 18842602830@163.com
 * Description
 */

public class MyApplication extends LibsApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
        DbHelper.getInstance().init(this);
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    @Override
    protected boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 8.0 通知栏适配
     */
    private void createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        // 显示角标，在发送通知的时候，加入 setNumber 方法
        channel.setShowBadge(true);
        notificationManager.createNotificationChannel(channel);

        /*NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                ...
                .setNumber(2)
                .build();
        manager.notify(2, notification);*/
    }

}
