package common.utils.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import common.utils.R;
import common.utils.databinding.LayoutBaseWebviewBinding;
import common.utils.utils.LogUtils;
import common.utils.view.ViewClick;
import common.utils.view.WebProgressBar;


/**
 * Created by wd on 2017/7/18.
 * 需要视频播放的，在 manifests 中的 activity 上加
 * android:configChanges="orientation|screenSize|keyboardHidden"
 * 在 activity 的 onCreate 中加 getWindow().setFormat(PixelFormat.TRANSLUCENT);
 */

public class BaseWebView extends RelativeLayout {

    private static final String TAG = BaseWebView.class.getSimpleName();

    private LayoutBaseWebviewBinding mBinding;
    private OnWebTitleChangeListener listener;
    private boolean showProgress = true;
    private boolean isLoadError = false;
    private String mUrl = "";

    //视频全屏相关
    private OnVideoViewListener mOnVideoViewListener;

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
        mBinding.empty.setOnClickListener(new ViewClick() {
            @Override
            public void onViewClick(View view) {
                isLoadError = false;
                mBinding.webViewAdv.setVisibility(View.VISIBLE);
                mBinding.empty.setVisibility(View.GONE);
                mBinding.webViewAdv.loadUrl(mUrl);
            }
        });
        WebSettings webSetting = mBinding.webViewAdv.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setDisplayZoomControls(false);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        // 是否支持viewport属性，默认值 false
        // 页面通过`<meta name="viewport" ... />`自适应手机屏幕
        // 当值为true且viewport标签不存在或未指定宽度时使用 wide viewport mode
        webSetting.setUseWideViewPort(true);
        // 是否使用overview mode加载页面，默认值 false
        // 当页面宽度大于WebView宽度时，缩小使页面宽度等于WebView宽度
//        settings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= 21) {
            webSetting.setMixedContentMode(0);
        }
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mBinding.webViewAdv.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        mBinding.webViewAdv.setBackgroundColor(0);//设置背景透明


        mBinding.webViewAdv.setWebChromeClient(new WebChromeClient() {

            View myVideoView;
            View myNormalView;

            @Override
            public void onHideCustomView() {
                if (mOnVideoViewListener != null) {
                    mOnVideoViewListener.hide();
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtils.d(TAG, "onProgressChanged: " + newProgress);
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
                if (!TextUtils.isEmpty(title) && title.toLowerCase().contains("error")) {
                    isLoadError = true;
                }
            }

            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = findViewById(R.id.web_view_adv);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;

                if (mOnVideoViewListener != null) {
                    mOnVideoViewListener.showCustomView(view, customViewCallback);
                }
            }

        });
        mBinding.webViewAdv.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                mUrl = url;
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
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (isLoadError) {
                    errorDeal();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                isLoadError = true;
            }

            @Override
            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, com.tencent.smtt.export.external.interfaces.SslError sslError) {
                sslErrorHandler.proceed();
                webView.reload();
            }

        });
    }

    private void errorDeal() {
        mBinding.webViewAdv.setVisibility(View.GONE);
        mBinding.empty.setVisibility(View.VISIBLE);
    }

    public BaseWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setLoadingColor(int color) {
        mBinding.webProgress.setColor(color);
    }

    public void setOnVideoViewListener(OnVideoViewListener mOnVideoViewListener) {
        this.mOnVideoViewListener = mOnVideoViewListener;
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
            LogUtils.i(TAG, url);
            mUrl = url;
            mBinding.webViewAdv.loadUrl(url);
        }
    }

    public void onPause() {
//        if (getContext() instanceof Activity) {
//            if (((Activity) getContext()).isFinishing()) {
//                mBinding.webViewAdv.pauseTimers();
//                mBinding.webViewAdv.loadUrl("about:blank");
//            }
//        }
//        mBinding.webViewAdv.onPause();
    }

    //
    public void onResume() {
//        mBinding.webViewAdv.resumeTimers();
//        mBinding.webViewAdv.onResume();
    }

    public void onDestroy() {
//        mBinding.webViewAdv.setWebViewClient(null);
//        mBinding.webViewAdv.setWebChromeClient(null);
//        mBinding.webViewAdv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//        mBinding.webViewAdv.clearHistory();
//        ((ViewGroup) mBinding.webViewAdv.getParent()).removeView(mBinding.webViewAdv);
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

    public interface OnVideoViewListener {

        void showCustomView(View view, IX5WebChromeClient.CustomViewCallback callback);

        void hide();
    }

}
