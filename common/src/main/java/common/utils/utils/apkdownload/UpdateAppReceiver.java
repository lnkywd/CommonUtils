package common.utils.utils.apkdownload;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author wd
 * @date 2018/05/14
 * Email 18842602830@163.com
 * Description
 */

public class UpdateAppReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        int notifyId = 1;
        int progress = intent.getIntExtra("progress", 0);
        String title = intent.getStringExtra("title");

        NotificationManager nm = null;
        if (UpdateAppUtils.showNotification) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle("正在下载 " + title);
            builder.setSmallIcon(context.getApplicationInfo().icon == 0 ? android.R.mipmap.sym_def_app_icon : context.getApplicationInfo().icon);
            builder.setProgress(100, progress, false);

            Notification notification = builder.build();
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(notifyId, notification);
        }


        if (progress == 100) {
            if (nm != null) {
                nm.cancel(notifyId);
            }

            if (DownloadAppUtils.downloadUpdateApkFilePath != null) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(DownloadAppUtils.downloadUpdateApkFilePath);
                if (UpdateAppUtils.needFitAndroidN && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(
                            context, context.getPackageName() + ".fileprovider", apkFile);
                    i.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(apkFile),
                            "application/vnd.android.package-archive");
                }
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
            if (UpdateAppUtils.isForce) {
                System.exit(0);
            }
        }
    }
}
