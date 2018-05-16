package common.utils.utils.apkdownload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

import common.utils.utils.ToastUtils;

/**
 * @author wd
 * @date 2018/05/14
 * Email 18842602830@163.com
 * Description
 */

public class DownloadAppUtils {

    private static final String TAG = DownloadAppUtils.class.getSimpleName();
    public static long downloadUpdateApkId = -1;//下载更新Apk 下载任务对应的Id
    public static String downloadUpdateApkFilePath;//下载更新Apk 文件路径

    private static ProgressDialog mDialog;

    /**
     * 通过浏览器下载APK包
     *
     * @param context
     * @param url
     */
    public static void downloadForWebView(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void download(final Context context, String url, final String serverVersionName, boolean showDialog) {

        String packageName = context.getPackageName();
        String filePath = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//外部存储卡
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            ToastUtils.showShort("下载失败，没有外部存储");
            return;
        }

        String apkLocalPath = filePath + File.separator + packageName + "_" + serverVersionName + ".apk";
        apkLocalPath = context.getExternalCacheDir().getAbsolutePath() + File.separator + serverVersionName + ".apk";
        downloadUpdateApkFilePath = apkLocalPath;

        FileDownloader.setup(context);

        FileDownloader.getImpl().create(url)
                .setPath(apkLocalPath)
                .setListener(new FileDownloadLargeFileListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        send(context, (int) (soFarBytes * 100.0 / totalBytes), serverVersionName);
                        if (mDialog != null && mDialog.isShowing()) {
                            mDialog.setProgress((int) (soFarBytes * 100.0 / totalBytes));
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                        UpdateAppUtils.isUpdating = false;
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        send(context, 100, serverVersionName);
                        if (mDialog != null) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        UpdateAppUtils.isUpdating = false;
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ToastUtils.showShort("下载出错");
                        if (mDialog != null) {
                            mDialog.dismiss();
                            mDialog = null;
                        }
                        UpdateAppUtils.isUpdating = false;
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        UpdateAppUtils.isUpdating = false;
                    }
                }).start();
        UpdateAppUtils.isUpdating = true;

        if ((context instanceof Activity && !((Activity) context).isFinishing()) && (showDialog || UpdateAppUtils.isForce)) {
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            if (UpdateAppUtils.isForce) {
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.setMessage("下载中...");
            dialog.show();
            mDialog = dialog;
        }
    }

    private static void send(Context context, int progress, String serverVersionName) {
        Intent intent = new Intent("common.utils.update");
        intent.putExtra("progress", progress);
        intent.putExtra("title", serverVersionName);
        context.sendBroadcast(intent);
    }

}
