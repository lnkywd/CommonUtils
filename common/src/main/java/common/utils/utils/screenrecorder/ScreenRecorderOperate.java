package common.utils.utils.screenrecorder;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaCodecInfo;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.Range;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Context.MEDIA_PROJECTION_SERVICE;
import static common.utils.utils.screenrecorder.ScreenRecorder.AUDIO_AAC;
import static common.utils.utils.screenrecorder.ScreenRecorder.VIDEO_AVC;

/**
 * @author wd
 * @date 2018/04/17
 * Email 18842602830@163.com
 * Description
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScreenRecorderOperate {

    private static final String ACTION_STOP = "net.yrom.screenrecorder.action.STOP";
    private static final int REQUEST_MEDIA_PROJECTION = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    private static final AtomicReference<ScreenRecorderOperate> INSTANCE = new AtomicReference<>();
    private MediaProjectionManager mMediaProjectionManager;
    private ScreenRecorder mRecorder;
    private WeakReference<Activity> mActivity;
    private MediaCodecInfo[] mAvcCodecInfos; // avc codecs
    private MediaCodecInfo[] mAacCodecInfos;
    private List<Integer> mAudioBitrate;
    private List<Integer> mAudioSampleRate;
    private ScreenRecorder.Callback callback;
    private String mFileName;
    private String mFilePath;
    private BroadcastReceiver mStopActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            File file = new File(mRecorder.getSavedPath());
            if (ACTION_STOP.equals(intent.getAction())) {
                stopRecorder();
            }
            StrictMode.VmPolicy vmPolicy = StrictMode.getVmPolicy();
            try {
                // disable detecting FileUriExposure on public file
                StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
                viewResult(file);
            } finally {
                StrictMode.setVmPolicy(vmPolicy);
            }
        }

        private void viewResult(File file) {
            Intent view = new Intent(Intent.ACTION_VIEW);
            view.addCategory(Intent.CATEGORY_DEFAULT);
            view.setDataAndType(Uri.fromFile(file), VIDEO_AVC);
            view.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                mActivity.get().startActivity(view);
            } catch (ActivityNotFoundException e) {
                // no activity can open this video
            }
        }
    };

    private ScreenRecorderOperate() {
    }

    public static ScreenRecorderOperate getInstance() {
        for (; ; ) {
            ScreenRecorderOperate current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new ScreenRecorderOperate();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public void setFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public void onButtonClick() {
        if (mRecorder != null) {
            stopRecorder();
        } else if (hasPermissions()) {
            startCaptureIntent();
        } else {
            requestPermissions();
        }
    }

    public void stopRecorder() {
        if (mRecorder != null) {
            mRecorder.quit();
        }
        mRecorder = null;
        try {
//            mActivity.get().unregisterReceiver(mStopActionReceiver);
        } catch (Exception e) {
            //ignored
        }
    }

    private boolean hasPermissions() {
        PackageManager pm = mActivity.get().getPackageManager();
        String packageName = mActivity.get().getPackageName();
        int granted = pm.checkPermission(RECORD_AUDIO, packageName) | pm.checkPermission(WRITE_EXTERNAL_STORAGE, packageName);
        return granted == PackageManager.PERMISSION_GRANTED;
    }

    private void startCaptureIntent() {
        Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
        mActivity.get().startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        final String[] permissions = new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO};
        boolean showRationale = false;
        for (String perm : permissions) {
            showRationale |= mActivity.get().shouldShowRequestPermissionRationale(perm);
        }
        if (!showRationale) {
            mActivity.get().requestPermissions(permissions, REQUEST_PERMISSIONS);
            return;
        }
        new AlertDialog.Builder(mActivity.get())
                .setMessage("Using your mic to record audio and your sd card to save video file")
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.get().requestPermissions(permissions, REQUEST_PERMISSIONS);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    public void init(ScreenRecorder.Callback callback, Activity mActivity) {
        this.callback = callback;
        this.mActivity = new WeakReference<>(mActivity);
        mMediaProjectionManager = (MediaProjectionManager) this.mActivity.get().getSystemService(MEDIA_PROJECTION_SERVICE);
        ScreenRecorderUtils.findEncodersByTypeAsync(VIDEO_AVC, new ScreenRecorderUtils.Callback() {
            @Override
            public void onResult(MediaCodecInfo[] infos) {
                mAvcCodecInfos = infos;
            }
        });
        ScreenRecorderUtils.findEncodersByTypeAsync(AUDIO_AAC, new ScreenRecorderUtils.Callback() {
            @Override
            public void onResult(MediaCodecInfo[] infos) {
                mAacCodecInfos = infos;
                MediaCodecInfo.CodecCapabilities capabilities = infos[0].getCapabilitiesForType(AUDIO_AAC);
                Range<Integer> bitrateRange = capabilities.getAudioCapabilities().getBitrateRange();
                int lower = Math.max(bitrateRange.getLower() / 1000, 80);
                int upper = bitrateRange.getUpper() / 1000;
                mAudioBitrate = new ArrayList<>();
                for (int rate = lower; rate < upper; rate += lower) {
                    mAudioBitrate.add(rate);
                }
                mAudioBitrate.add(upper);

                int[] sampleRates = capabilities.getAudioCapabilities().getSupportedSampleRates();
                mAudioSampleRate = new ArrayList<>(sampleRates.length);
//                int preferred = -1;
                for (int i = 0; i < sampleRates.length; i++) {
                    int sampleRate = sampleRates[i];
//                    if (sampleRate == 44100) {
//                        preferred = i;
//                    }
                    mAudioSampleRate.add(sampleRate);
                }

            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS) {
            int granted = PackageManager.PERMISSION_GRANTED;
            for (int r : grantResults) {
                granted |= r;
            }
            if (granted == PackageManager.PERMISSION_GRANTED) {
                startCaptureIntent();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            // NOTE: Should pass this result data into a Service to run ScreenRecorder.
            // The following codes are merely exemplary.

            MediaProjection mediaProjection = mMediaProjectionManager.getMediaProjection(resultCode, data);
            if (mediaProjection == null) {
                Log.e("@@", "media projection is null");
                return;
            }

            VideoEncodeConfig video = createVideoConfig();
            AudioEncodeConfig audio = createAudioConfig(); // audio can be null
            if (video == null) {
                mediaProjection.stop();
                return;
            }

            File dir = new File(mActivity.get().getExternalCacheDir(), "ScreenCaptures");
            if (!dir.exists() && !dir.mkdirs()) {
                cancelRecorder();
                return;
            }
            if (TextUtils.isEmpty(mFileName)) {
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
                mFileName = String.format("Screen-%s-%sx%s.mp4", format.format(new Date()), video.width, video.height);
            }
            File file = new File(dir, mFileName);
            mFilePath = file.getAbsolutePath();
            mRecorder = new ScreenRecorder(video, audio, 1, mediaProjection, file.getAbsolutePath());
            mRecorder.setCallback(callback);
            if (hasPermissions()) {
                startRecorder();
            } else {
                cancelRecorder();
            }
        }
    }

    private VideoEncodeConfig createVideoConfig() {
        // 直接用的获取到的第一个编码模式
        final String codec = mAvcCodecInfos[0].getName();
        if (codec == null) {
            // no selected codec ??
            return null;
        }
        // video size
        int[] selectedWithHeight = getSelectedWithHeight();
        int width = selectedWithHeight[1];
        int height = selectedWithHeight[0];
        int framerate = 15;
        int iframe = 5;
        int bitrate = 800 * 1000;
        MediaCodecInfo.CodecProfileLevel profileLevel = ScreenRecorderUtils.toProfileLevel("Default");
        return new VideoEncodeConfig(width, height, bitrate,
                framerate, iframe, codec, VIDEO_AVC, profileLevel);
    }

    private AudioEncodeConfig createAudioConfig() {
        String codec = mAacCodecInfos[0].getName();
        if (codec == null) {
            return null;
        }
        int bitrate = getSelectedAudioBitrate();
        int samplerate = getSelectedAudioSampleRate();
        int channelCount = 1;
        int profile = getSelectedAudioProfile();

        return new AudioEncodeConfig(codec, AUDIO_AAC, bitrate, samplerate, channelCount, profile);
    }

    private void cancelRecorder() {
        if (mRecorder == null) return;
        stopRecorder();
    }

    private void startRecorder() {
        if (mRecorder == null) return;
        mRecorder.start();
//        mActivity.get().registerReceiver(mStopActionReceiver, new IntentFilter(ACTION_STOP));
    }

    private int[] getSelectedWithHeight() {
//         <item>480x360</item>
//        <item>720x480</item>
//        <item>1280x720</item>
//        <item>1920x1080</item>
//        <item>3860x2160</item>
        return new int[]{1280, 720};
    }

    private int getSelectedAudioBitrate() {
        return mAudioBitrate.get(0) * 1000; // bps
    }

    private int getSelectedAudioSampleRate() {
        int selectedItem = mAudioSampleRate.contains(44100) ? 44100 : mAudioSampleRate.get(0);
        return selectedItem;
    }

    private int getSelectedAudioProfile() {
        String[] profiles = ScreenRecorderUtils.aacProfiles();
        String selectedItem = profiles[0];
        MediaCodecInfo.CodecProfileLevel profileLevel = ScreenRecorderUtils.toProfileLevel(selectedItem);
        return profileLevel == null ? MediaCodecInfo.CodecProfileLevel.AACObjectMain : profileLevel.profile;
    }

    public void onDestory() {
        stopRecorder();
        mActivity = null;
        callback = null;
    }

    public String getFilePath() {
        return mFilePath;
    }


}
