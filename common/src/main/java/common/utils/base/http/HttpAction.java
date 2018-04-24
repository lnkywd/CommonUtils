package common.utils.base.http;

import android.content.Context;
import android.text.TextUtils;

import common.utils.utils.NetworkUtils;
import common.utils.utils.ToastUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by MAC on 2017/4/7.
 */

public abstract class HttpAction<T> implements Observer<T> {

    private Context context;
    /**
     * 是否静默请求
     */
    private boolean quiet = false;

    public HttpAction(Context context) {
        this.context = context;
    }

    public HttpAction(Context context, boolean quiet) {
        this.context = context;
        this.quiet = quiet;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        String code = null;
        String msg = null;
        if (null != t) {
            if (!(t instanceof ApiResponseWraper)) {
                onHttpError(null);
            }
            code = ((ApiResponseWraper) t).getCode();
            msg = ((ApiResponseWraper) t).getMessage();
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
        onHttpComplete();
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

    public abstract void onHttpError(Response response);

    protected boolean customCodeDeal(int code) {
        return false;
    }

    protected String successCode() {
        return "200";
    }

    public abstract void onHttpSuccess(T data);

    public void onHttpSuccess(T data, String code, String msg) {
        onHttpSuccess(data, code, msg, true);
    }

    public abstract void onHttpComplete();

    public void onHttpSuccess(T data, String code, String msg, boolean showToast) {
        if (showToast && !quiet) {
            ToastUtils.showShort(msg);
        }
    }

}