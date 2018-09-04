package common.utils.utils;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;

/**
 * @author wd
 * @date 2018/08/14
 * Email 18842602830@163.com
 * Description
 */

public class VideoUtils {
    /**
     * 随机截取视频图片
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        return createVideoThumbnail(filePath, -1);
    }

    /**
     * 截取视频图片
     *
     * @param filePath the path of video file
     */
    public static Bitmap createVideoThumbnail(String filePath, long timeUs) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int flag = MediaMetadataRetriever.OPTION_CLOSEST;
        try {
            retriever.setDataSource(filePath);
            if (timeUs == -2) {
                timeUs = (Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) - 100) * 1000;
                flag = MediaMetadataRetriever.OPTION_PREVIOUS_SYNC;
            }
            bitmap = retriever.getFrameAtTime(timeUs, flag);
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        return bitmap;
    }

    /**
     * 截取视频最后一帧图片
     * 大概率截取视频最后一帧图片。截得是关键帧。
     */
    public static Bitmap createVideoLastThumbnail(String filePath) {
        return createVideoThumbnail(filePath, -2);
    }
}
