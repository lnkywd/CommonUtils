package common.utils.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import common.utils.R;

/**
 * Created by MAC on 2017/3/28.
 */

public class GlideUtils {

    /**
     * 加载图片
     */
    @BindingAdapter("image")
    public static void displayImage(ImageView imageView, String image_url) {
        if (image_url == null) {
            image_url = "";
        }
        Glide.with(imageView.getContext())
                .load(image_url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_default)
                        .error(R.drawable.ic_default)
                        .dontAnimate())
                .into(imageView);
    }

    /**
     * 加载头像
     */
    public static void displayHead(ImageView imageview, String url) {
        if (url == null) {
            url = "";
        }
        Glide.with(imageview.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.mipmap.icon_default_head)
                        .placeholder(R.mipmap.icon_default_head)
                        .dontAnimate())
                .into(imageview);
    }

    /**
     * 加载图片
     */
    public static void displayImage(Context context, ImageView imageView, String image_url) {
        if (image_url == null) {
            image_url = "";
        }
        Glide.with(context)
                .load(image_url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .dontAnimate())
                .into(imageView);
    }

    /**
     * 展示矩形圆角图片
     */
    public static void displayRoundImage(Context context, ImageView imageView, String url) {
        displayRoundImage(context, imageView, url, -1);
    }

    /**
     * 展示矩形圆角图片
     */
    public static void displayRoundImage(Context context, ImageView imageView, int res) {
        displayRoundImage(context, imageView, "", res);
    }

    /**
     * 展示矩形圆角图片
     */
    public static void displayRoundImage(Context context, ImageView imageView, String url, int res) {
        if (url == null) {
            url = "";
        }
        Glide.with(context)
                .load(TextUtils.isEmpty(url) ? res : url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .dontAnimate())
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(), new RoundedCorners(PixelUtils.dp2px(6)))))
                .into(imageView);
    }

    /**
     * 展示矩形圆角 原有图片
     */
    public static void displayRoundImage(ImageView imageView, String url) {
        if (url == null) {
            url = "";
        }
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .dontAnimate())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(PixelUtils.dp2px(6))))
                .into(imageView);
    }

}
