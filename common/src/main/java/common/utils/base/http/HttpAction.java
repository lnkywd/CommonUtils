package common.utils.base.http;

import android.text.TextUtils;

import common.utils.utils.NetworkUtils;
import common.utils.utils.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public abstract class HttpAction<T> implements Observer<T> {

    /**
     * 是否静默请求
     */
    private boolean quiet = false;
    /**
     * 是否是外部 url 请求，不走状态码判断
     */
    private boolean isOtherRequest = false;

    public HttpAction() {
    }

    public HttpAction(boolean quiet) {
        this.quiet = quiet;
    }

    public HttpAction(boolean quiet, boolean isOtherRequest) {
        this.quiet = quiet;
        this.isOtherRequest = isOtherRequest;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (null != t) {
            if (isOtherRequest) {
                onHttpSuccess(t);
                return;
            }
            String code;
            String msg;
            if (t instanceof ApiResponseWraper) {
                code = ((ApiResponseWraper) t).getCode();
                msg = ((ApiResponseWraper) t).getMessage();
            } else if (t instanceof ApiResponseListWraper) {
                code = ((ApiResponseListWraper) t).getCode();
                msg = ((ApiResponseListWraper) t).getMessage();
            } else {
                onHttpError(null);
                return;
            }
            if (code == null || TextUtils.isEmpty(code) || customCodeDeal(Integer.parseInt(code))) {
                onHttpError(null);
                return;
            }
            if (TextUtils.equals(successCode(), code)) {
                onHttpSuccess(t);
            } else {
                onHttpSuccess(t, code, msg);
            }
        } else {
            if (!quiet) {
                ToastUtils.showShort("数据错误");
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        onHttpComplete();
        if (NetworkUtils.isConnected()) {
            onHttpError(null);
        } else {
            if (quiet) {
                return;
            }
            ToastUtils.showShort("网络错误，请检查网络情况");
        }
    }

    @Override
    public void onComplete() {
        onHttpComplete();
    }

    public abstract void onHttpComplete();

    public abstract void onHttpSuccess(T data);

    public abstract void onHttpError(Response response);

    protected boolean customCodeDeal(int code) {
        return false;
    }

    protected String successCode() {
        return "200";
    }

    public void onHttpSuccess(T data, String code, String msg) {
        onHttpSuccess(data, code, msg, true);
    }

    public void onHttpSuccess(T data, String code, String msg, boolean showToast) {
        if (showToast && !quiet) {
            ToastUtils.showShort(msg);
        }
    }

}