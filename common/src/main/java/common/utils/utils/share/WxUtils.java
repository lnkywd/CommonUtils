package common.utils.utils.share;

import android.content.Context;
import android.graphics.Bitmap;

import com.blankj.utilcode.util.ImageUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import common.utils.LibsApplication;
import common.utils.utils.ToastUtils;

/**
 * @author wd
 * @date 2018/10/16
 * Email 18842602830@163.com
 * Description 用于登录和相关分享
 */

public class WxUtils {

    /**
     * 未安装微信或微信版本过低
     */
    public static final int NO_OR_LOW_WX = 1;
    /**
     * 登录失败
     */
    public static final int ERROR_LOGIN = 2;

    private static WxUtils mWxLoginUtils;
    private IWXAPI mWXApi;
    private WxResultCallBack mCallback;
    private WxResultCallBack commonShareCallBack = new WxResultCallBack() {
        @Override
        public void onSuccess(BaseResp baseResp) {
            ToastUtils.showShort("分享成功");
        }

        @Override
        public void onError(int code) {
            ToastUtils.showShort("分享失败");
        }

        @Override
        public void onCancel() {
            ToastUtils.showShort("分享取消");
        }
    };

    public WxUtils(Context context, String wxAppid) {
        this.mWXApi = WXAPIFactory.createWXAPI(context, (String) null);
        this.mWXApi.registerApp(wxAppid);
    }

    public static void init(String wxId) {
        if (mWxLoginUtils == null) {
            mWxLoginUtils = new WxUtils(LibsApplication.getInstance().getApplicationContext(), wxId);
        }
    }

    public void shareText(String text, SharePlace sharePlace, WxResultCallBack callBack) {
        setCallBack(callBack);
        WXTextObject wxTextObject = new WXTextObject();
        wxTextObject.text = text;

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxTextObject;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = getCurrTime();

        req.message = msg;
        switch (sharePlace) {
            case Friend:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Zone:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case Favorites:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                break;
        }

        if (getInstance().getWXApi() != null) {
            getInstance().getWXApi().sendReq(req);
        } else {
            throw new NullPointerException("请先调用WechatShare.init()方法");
        }
    }

    private void setCallBack(WxResultCallBack callBack) {
        if (callBack != null) {
            mCallback = callBack;
        } else {
            mCallback = commonShareCallBack;
        }
    }

    public static String getCurrTime() {
        Date now = new Date();
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String s = outFormat.format(now);
        return s;
    }

    public IWXAPI getWXApi() {
        return this.mWXApi;
    }

    public static WxUtils getInstance() {
        return mWxLoginUtils;
    }

    public void shareImage(Bitmap bitmap, SharePlace sharePlace) {
        shareImage(bitmap, sharePlace, null);
    }

    public void shareImage(Bitmap bitmap, SharePlace sharePlace, WxResultCallBack callBack) {
        setCallBack(callBack);
        WXImageObject wxImageObject = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxImageObject;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        bitmap.recycle();
        msg.thumbData = ImageUtils.bitmap2Bytes(thumbBmp, Bitmap.CompressFormat.PNG);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = getCurrTime();

        req.message = msg;
        switch (sharePlace) {
            case Friend:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Zone:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case Favorites:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                break;
        }

        if (mWXApi != null) {
            mWXApi.sendReq(req);
        } else {
            throw new NullPointerException("请先调用WechatShare.init()方法");
        }
    }

    public void shareMusic(WechatShareModel shareModel, SharePlace sharePlace) {
        shareMusic(shareModel, sharePlace, null);
    }

    public void shareMusic(WechatShareModel shareModel, SharePlace sharePlace, WxResultCallBack callBack) {
        setCallBack(callBack);
        WXMusicObject wxMusicObject = new WXMusicObject();
        wxMusicObject.musicUrl = shareModel.getUrl();

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = wxMusicObject;
        msg.title = shareModel.getTitle();
        msg.description = shareModel.getDescription();

        msg.thumbData = shareModel.getThumbData();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = getCurrTime();
        req.message = msg;

        switch (sharePlace) {
            case Friend:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Zone:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case Favorites:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                break;
        }

        if (mWXApi != null) {
            mWXApi.sendReq(req);
        } else {
            throw new NullPointerException("请先调用WechatShare.init()方法");
        }
    }

    public void shareVideo(WechatShareModel shareModel, SharePlace sharePlace) {
        shareVideo(shareModel, sharePlace, null);
    }

    public void shareVideo(WechatShareModel shareModel, SharePlace sharePlace, WxResultCallBack callBack) {
        setCallBack(callBack);
        WXVideoObject wxVideoObject = new WXVideoObject();
        wxVideoObject.videoUrl = shareModel.getUrl();

        WXMediaMessage msg = new WXMediaMessage(wxVideoObject);
        msg.title = shareModel.getTitle();
        msg.description = shareModel.getDescription();

        msg.thumbData = shareModel.getThumbData();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = getCurrTime();
        req.message = msg;

        switch (sharePlace) {
            case Friend:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Zone:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case Favorites:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                break;

        }

        if (mWXApi != null) {
            mWXApi.sendReq(req);
        } else {
            throw new NullPointerException("请先调用WechatShare.init()方法");
        }
    }

    public void shareURL(WechatShareModel shareModel, SharePlace sharePlace) {
        shareURL(shareModel, sharePlace, null);
    }

    public void shareURL(WechatShareModel shareModel, SharePlace sharePlace, WxResultCallBack callBack) {
        setCallBack(callBack);
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = shareModel.getUrl();

        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = shareModel.getTitle();
        msg.description = shareModel.getDescription();

        msg.thumbData = shareModel.getThumbData();

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = getCurrTime();
        req.message = msg;

        switch (sharePlace) {
            case Friend:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Zone:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
            case Favorites:
                req.scene = SendMessageToWX.Req.WXSceneFavorite;
                break;
            default:
                break;
        }

        if (mWXApi != null) {
            mWXApi.sendReq(req);
        } else {
            throw new NullPointerException("请先调用WechatShare.init()方法");
        }
    }

    public void doLogin(WxResultCallBack callBack) {
        this.mCallback = callBack;
        if (!check()) {
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        mWXApi.sendReq(req);
    }

    private boolean check() {
        boolean isWXAppInstalled = mWXApi.isWXAppInstalled();
        if (!isWXAppInstalled) {
            ToastUtils.showShort("请先去下载并安装微信，谢谢");
        }
        return isWXAppInstalled;
    }

    public void onResp(BaseResp baseResp, int errorCode) {
        if (this.mCallback != null) {
            if (errorCode == 0) {
                this.mCallback.onSuccess(baseResp);
            } else if (errorCode == -1) {
                this.mCallback.onError(ERROR_LOGIN);
            } else if (errorCode == -2) {
                this.mCallback.onCancel();
            }

            this.mCallback = null;
        }
    }

    public enum SharePlace {
        Friend,
        Zone,
        Favorites
    }

    public interface WxResultCallBack {

        void onSuccess(BaseResp baseResp);

        void onError(int code);

        void onCancel();

    }

}
