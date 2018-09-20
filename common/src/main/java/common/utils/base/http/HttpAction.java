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

    /**
     * 是否调用了 complete
     */
    private boolean hasRequest = false;


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
                httpComplete();
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
                onHttpActionError(new MyCustomException(MyCustomException.ENTITY_ERROR));
                httpComplete();
                return;
            }
            if (code == null || TextUtils.isEmpty(code) || customCodeDeal(Integer.parseInt(code))) {
                onHttpError(null);
                onHttpActionError(new MyCustomException(code, MyCustomException.CODE_ERROR));
                httpComplete();
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
        httpComplete();
    }

    @Override
    public void onError(Throwable t) {
        httpComplete();
        if (NetworkUtils.isConnected()) {
            onHttpError(null);
            onHttpActionError(new MyCustomException(t, MyCustomException.RX_ERROR));
        } else {
            if (quiet) {
                return;
            }
            ToastUtils.showShort("网络错误，请检查网络情况");
        }
    }

    @Override
    public void onComplete() {
        httpComplete();
    }

    public abstract void onHttpSuccess(T data);

    private void httpComplete() {
        if (!hasRequest) {
            hasRequest = true;
            onHttpComplete();
        }
    }

    @Deprecated
    public void onHttpError(Response response) {

    }

    public void onHttpActionError(MyCustomException myCustomException) {

    }

    protected boolean customCodeDeal(int code) {
        return false;
    }

    protected String successCode() {
        return "200";
    }

    public void onHttpSuccess(T data, String code, String msg) {
        onHttpSuccess(data, code, msg, true);
    }

    public abstract void onHttpComplete();

    public void onHttpSuccess(T data, String code, String msg, boolean showToast) {
        if (showToast && !quiet) {
            ToastUtils.showShort(msg);
        }
    }

    public void onHttpError() {

    }

}