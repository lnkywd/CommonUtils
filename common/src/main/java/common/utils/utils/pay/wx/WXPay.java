package common.utils.utils.pay.wx;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import common.utils.utils.ToastUtils;

/**
 * @author wd
 * @date 2018/07/30
 * Email 18842602830@163.com
 * Description
 * <p>
 * 注册 activity
 * <activity
 * android:name="common.utils.utils.pay.wx.WXPayCallbackActivity"
 * android:configChanges="orientation|keyboardHidden|navigation|screenSize"
 * android:launchMode="singleTop"
 * android:theme="@android:style/Theme.Translucent.NoTitleBar" />
 * <p>
 * <activity-alias
 * android:name=".wxapi.WXPayEntryActivity"
 * android:exported="true"
 * android:targetActivity="common.utils.utils.pay.wx.WXPayCallbackActivity" />
 * <p>
 * 要在支付前调用
 * WXPay.init(getApplicationContext(), wx_appid);
 */

public class WXPay {

    /**
     * 未安装微信或微信版本过低
     */
    public static final int NO_OR_LOW_WX = 1;
    /**
     * 支付参数错误
     */
    public static final int ERROR_PAY_PARAM = 2;
    /**
     * 支付失败
     */
    public static final int ERROR_PAY = 3;

    private static WXPay mWXPay;
    private IWXAPI mWXApi;
    private WXPayResultCallBack mCallback;

    public WXPay(Context context, String wxAppid) {
        mWXApi = WXAPIFactory.createWXAPI(context, null);
        mWXApi.registerApp(wxAppid);
    }

    public static void init(Context context, String wxAppid) {
        if (mWXPay == null) {
            mWXPay = new WXPay(context, wxAppid);
        }
    }

    public static WXPay getInstance() {
        return mWXPay;
    }

    public IWXAPI getWXApi() {
        return mWXApi;
    }

    public void doPay(PayReq payReq, WXPayResultCallBack callback) {
        doPay(payReq.appId, payReq.partnerId, payReq.prepayId, payReq.packageValue, payReq.nonceStr, payReq.timeStamp, payReq.sign, callback);
    }

    /**
     * 发起微信支付
     */
    public void doPay(String appId, String partnerId, String prepayId, String packageValue, String nonceStr, String timeStamp,
                      String sign, WXPayResultCallBack callback) {
        mCallback = callback;

        if (!check()) {
            ToastUtils.showShort("请先安装微信");
            if (mCallback != null) {
                mCallback.onError(NO_OR_LOW_WX);
            }
            return;
        }

        if (TextUtils.isEmpty(appId) || TextUtils.isEmpty(partnerId) ||
                TextUtils.isEmpty(prepayId) || TextUtils.isEmpty(packageValue) ||
                TextUtils.isEmpty(nonceStr) || TextUtils.isEmpty(timeStamp) ||
                TextUtils.isEmpty(sign)) {
            if (mCallback != null) {
                mCallback.onError(ERROR_PAY_PARAM);
            }
            return;
        }

        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = partnerId;
        req.prepayId = prepayId;
        req.packageValue = packageValue;
        req.nonceStr = nonceStr;
        req.timeStamp = timeStamp;
        req.sign = sign;

        mWXApi.sendReq(req);
    }

    /**
     * 检测是否支持微信支付
     */
    private boolean check() {
        return mWXApi.isWXAppInstalled() && mWXApi.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
    }

    /**
     * 支付回调响应
     */
    public void onResp(int errorCode) {
        if (mCallback == null) {
            return;
        }

        if (errorCode == 0) {   //成功
            mCallback.onSuccess();
        } else if (errorCode == -1) {   //错误
            mCallback.onError(ERROR_PAY);
        } else if (errorCode == -2) {   //取消
            mCallback.onCancel();
        }

        mCallback = null;
    }

    public interface WXPayResultCallBack {
        void onSuccess(); //支付成功

        void onError(int errorCode);   //支付失败

        void onCancel();    //支付取消
    }

}
