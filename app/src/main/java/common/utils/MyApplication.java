package common.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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
