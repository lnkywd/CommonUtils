package common.utils.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import common.utils.R;
import common.utils.databinding.LayoutBaseWebviewBinding;
import common.utils.utils.ToastUtils;
import common.utils.view.WebProgressBar;


/**
 * Created by wd on 2017/7/18.
 */

public class BaseWebView extends RelativeLayout {

    private static final String TAG = BaseWebView.class.getSimpleName();

    private LayoutBaseWebviewBinding mBinding;
    private OnWebTitleChangeListener listener;
    private boolean showProgress = false;

    public BaseWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_base_webview,
                this, true);
        mBinding.webProgress.setOnEndListener(new WebProgressBar.OnEndListener() {
            @Override
            public void onEnd() {
                mBinding.webProgress.setVisibility(View.GONE);
            }
        });
        WebSettings settings = mBinding.webViewAdv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        // 当值为true且viewport标签不存在或未指定宽度时使用 wide viewport mode
        settings.setUseWideViewPort(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
//        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mBinding.webViewAdv.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        mBinding.webViewAdv.setBackgroundColor(0);//设置背景透明
        mBinding.webViewAdv.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.d(TAG, "onProgressChanged: " + newProgress);
                mBinding.webProgress.setNormalProgress(newProgress);
                if (newProgress == 100) {
                    if (listener != null) {
                        listener.onLoadFinish();
                    }
                    mBinding.webProgress.setFinishProgress();
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!TextUtils.isEmpty(title) && !TextUtils.equals("about:blank", title)) {
                    if (listener != null) {
                        listener.changeTitle(title);
                    }
                }
            }
        });
        mBinding.webViewAdv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (showProgress) {
                    mBinding.webProgress.setVisibility(View.VISIBLE);
                    mBinding.webProgress.setDrawProgress();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mBinding.webViewAdv.setVisibility(View.GONE);
                ToastUtils.showShort("加载失败");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                view.reload();
            }
        });
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void registerJavascriptInterfaces(Object javascriptInterface, String jsInterfaceName) {
        mBinding.webViewAdv.addJavascriptInterface(javascriptInterface, jsInterfaceName);
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public void setListener(OnWebTitleChangeListener listener) {
        this.listener = listener;
    }

    public WebView getWebView() {
        return mBinding.webViewAdv;
    }

    public void setUrl(String url) {
        if (mBinding != null) {
            mBinding.webViewAdv.loadUrl(url);
        }
    }

    public void onPause() {
        if (getContext() instanceof Activity) {
            if (((Activity) getContext()).isFinishing()) {
                mBinding.webViewAdv.pauseTimers();
                mBinding.webViewAdv.loadUrl("about:blank");
            }
        }
        mBinding.webViewAdv.onPause();
    }

    public void onResume() {
        mBinding.webViewAdv.resumeTimers();
        mBinding.webViewAdv.onResume();
    }

    public void onDestroy() {
        mBinding.webViewAdv.setWebViewClient(null);
        mBinding.webViewAdv.setWebChromeClient(null);
        mBinding.webViewAdv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        mBinding.webViewAdv.clearHistory();
        ((ViewGroup) mBinding.webViewAdv.getParent()).removeView(mBinding.webViewAdv);
        mBinding.webViewAdv.destroy();
    }

    public boolean onBackPressed() {
        if (mBinding.webViewAdv != null && mBinding.webViewAdv.canGoBack()) {
            mBinding.webViewAdv.goBack();// 返回前一个页面
            return false;
        }
        return true;
    }

    public interface OnWebTitleChangeListener {

        void changeTitle(String title);

        void onLoadFinish();

    }

}
