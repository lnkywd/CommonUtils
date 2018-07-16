package common.utils.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by MAC on 2017/3/28.
 */

public class GlideUtils {

    private static int commonDefault = 0;
    private static int headDefault = 0;

    public static void setCommonDefault(int commonDefault) {
        GlideUtils.commonDefault = commonDefault;
    }

    public static void setHeadDefault(int headDefault) {
        GlideUtils.headDefault = headDefault;
    }

    /**
     * 加载图片
     */
    public static void displayImage(ImageView imageView, Object img) {
        if (isValidContextForGlide(imageView.getContext())) {
            return;
        }
        if (img == null) {
            img = "";
        }
        Glide.with(imageView.getContext())
                .load(img)
                .apply(new RequestOptions()
                        .placeholder(commonDefault)
                        .error(commonDefault)
                        .dontAnimate())
                .into(imageView);
    }

    /**
     * 判断 context 是否能用
     *
     * @return true 为不能用，false 为能用
     */
    private static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return true;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载头像
     */
    public static void displayHead(ImageView imageView, Object img) {
        if (isValidContextForGlide(imageView.getContext())) {
            return;
        }
        if (img == null) {
            img = "";
        }
        Glide.with(imageView.getContext())
                .load(img)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(headDefault)
                        .placeholder(headDefault)
                        .dontAnimate())
                .into(imageView);
    }

    /**
     * 展示矩形圆角图片
     */
    public static void displayRoundCenterImage(ImageView imageView, Object img) {
        if (isValidContextForGlide(imageView.getContext())) {
            return;
        }
        if (img == null) {
            img = "";
        }
        Glide.with(imageView.getContext())
                .load(img)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(commonDefault)
                        .placeholder(commonDefault)
                        .dontAnimate())
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(PixelUtils.dp2px(6)))))
                .into(imageView);
    }

    /**
     * 展示矩形圆角 原有图片
     */
    public static void displayRoundImage(ImageView imageView, Object img) {
        if (isValidContextForGlide(imageView.getContext())) {
            return;
        }
        if (img == null) {
            img = "";
        }
        Glide.with(imageView.getContext())
                .load(img)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(commonDefault)
                        .placeholder(commonDefault)
                        .dontAnimate())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(PixelUtils.dp2px(6))))
                .into(imageView);
    }

}
