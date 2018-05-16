package common.utils.utils.apkdownload;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.liulishuo.filedownloader.FileDownloader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import common.utils.utils.ToastUtils;

/**
 * @author wd
 * @date 2018/05/14
 * Email 18842602830@163.com
 * Description
 */

public class UpdateAppUtils {

    public static final int DOWNLOAD_BY_APP = 1003;
    public static final int DOWNLOAD_BY_BROWSER = 1004;
    /**
     * 根据版本名字判断是否需要更新
     */
    public static final int CHECK_BY_VERSION_NAME = 1001;
    /**
     * 根据版本号判断
     */
    public static final int CHECK_BY_VERSION_CODE = 1002;
    /**
     * 外部判断是否需要更新
     */
    public static final int CHECK_BY_OUT = 1003;
    /**
     * 提供给 整个工程不需要适配到7.0的项目 置为false
     */
    public static boolean needFitAndroidN = false;
    public static boolean showNotification = true;
    /**
     * 是否在下载应用中
     */
    public static boolean isUpdating = false;
    /**
     * 是否强制更新
     */
    public static boolean isForce = false;
    /**
     * 是否显示升级进度对话框
     */
    public boolean showProgressDialog = false;
    private Activity activity;
    private int checkBy = CHECK_BY_VERSION_CODE;
    private int downloadBy = DOWNLOAD_BY_APP;
    private int serverVersionCode = 0;
    private String apkPath = "";
    private String serverVersionName = "";
    private int localVersionCode = 0;
    private String localVersionName = "";
    private String updateInfo = "";
    /**
     * 是否静默更新
     */
    private boolean isSilence = true;


    private UpdateAppUtils(Activity activity) {
        this.activity = activity;
        getAPPLocalVersion(activity);
    }

    //获取apk的版本号 currentVersionCode
    private void getAPPLocalVersion(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            localVersionName = info.versionName; // 版本名
            localVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static UpdateAppUtils from(Activity activity) {
        return new UpdateAppUtils(activity);
    }

    public static void closeDownload() {
        FileDownloader.getImpl().pauseAll();
    }

    public UpdateAppUtils needFitAndroidN(boolean needFitAndroidN) {
        UpdateAppUtils.needFitAndroidN = needFitAndroidN;
        return this;
    }

    public UpdateAppUtils checkBy(@Type int checkBy) {
        this.checkBy = checkBy;
        return this;
    }

    public UpdateAppUtils isSilence(boolean isSilence) {
        this.isSilence = isSilence;
        return this;
    }

    public UpdateAppUtils apkPath(String apkPath) {
        this.apkPath = apkPath;
        return this;
    }

    public UpdateAppUtils downloadBy(int downloadBy) {
        this.downloadBy = downloadBy;
        return this;
    }

    public UpdateAppUtils showNotification(boolean showNotification) {
        UpdateAppUtils.showNotification = showNotification;
        return this;
    }

    public UpdateAppUtils showProgressDialog(boolean showProgressDialog) {
        this.showProgressDialog = showProgressDialog;
        return this;
    }

    public UpdateAppUtils updateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
        return this;
    }

    public UpdateAppUtils serverVersionCode(int serverVersionCode) {
        this.serverVersionCode = serverVersionCode;
        return this;
    }

    public UpdateAppUtils serverVersionName(String serverVersionName) {
        this.serverVersionName = serverVersionName;
        return this;
    }

    public UpdateAppUtils isForce(boolean isForce) {
        UpdateAppUtils.isForce = isForce;
        return this;
    }

    public void update() {
        if (isUpdating) {
            ToastUtils.showShort("应用正在下载，请稍候");
            return;
        }
        if (TextUtils.isEmpty(apkPath)) {
            ToastUtils.showShort("apk下载地址不能为空");
            return;
        }
        if (TextUtils.isEmpty(serverVersionName)) {
            ToastUtils.showShort("服务器版本号不能为空");
            return;
        }
        switch (checkBy) {
            case CHECK_BY_VERSION_CODE:
                if (serverVersionCode > localVersionCode) {
                    toUpdate();
                } else {
                    if (isSilence) {
                        return;
                    }
                    ToastUtils.showShort("您当前已为最新版本");
                }
                break;

            case CHECK_BY_VERSION_NAME:
                if (!serverVersionName.equals(localVersionName)) {
                    toUpdate();
                } else {
                    if (isSilence) {
                        return;
                    }
                    ToastUtils.showShort("您当前已为最新版本");
                }
                break;
            case CHECK_BY_OUT:
                toUpdate();
                break;
            default:
        }

    }

    private void toUpdate() {
        String content = "发现新版本:" + serverVersionName + "\u3000是否下载更新?";
        if (!TextUtils.isEmpty(updateInfo)) {
            content = "发现新版本:" + serverVersionName + "\u3000是否下载更新?\n\n" + updateInfo;
        }
        new AlertDialog.Builder(activity)
                .setTitle("应用更新")
                .setMessage(content)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        realUpdate();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(isForce ? "" : "以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isForce) {
                            System.exit(0);
                        }
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .create().show();
    }

    private void realUpdate() {
        if (downloadBy == DOWNLOAD_BY_APP) {
            if (isWifiConnected(activity)) {
                DownloadAppUtils.download(activity, apkPath, serverVersionName, showProgressDialog);
            } else {
                new AlertDialog.Builder(activity)
                        .setTitle("提示")
                        .setMessage("目前手机不是WiFi状态\n确认是否继续下载更新？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (isForce) {
                                    System.exit(0);
                                }
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DownloadAppUtils.download(activity, apkPath, serverVersionName, showProgressDialog);
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }

        } else if (downloadBy == DOWNLOAD_BY_BROWSER) {
            DownloadAppUtils.downloadForWebView(activity, apkPath);
        }

    }

    /**
     * 检测wifi是否连接
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CHECK_BY_VERSION_NAME, CHECK_BY_VERSION_CODE, CHECK_BY_OUT})
    public @interface Type {

    }


}
