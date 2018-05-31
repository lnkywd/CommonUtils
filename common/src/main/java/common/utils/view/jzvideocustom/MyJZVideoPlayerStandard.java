package common.utils.view.jzvideocustom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * @author wd
 * @date 2018/05/30
 * Email 18842602830@163.com
 * Description
 * 监听到视频播放的生命周期和播放状态
 */

public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {

    public OnJZVideoStateListener mListener;

    public MyJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnJZVideoStateListener(OnJZVideoStateListener listener) {
        this.mListener = listener;
    }

    @Override
    public void init(Context context) {
        super.init(context);
    }

    @Override
    public int getLayoutId() {
        return cn.jzvd.R.layout.jz_layout_standard;
    }

    @Override
    public void onStateNormal() {
        super.onStateNormal();
        if (mListener != null) {
            mListener.onStateNormal();
        }
    }

    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
        if (mListener != null) {
            mListener.onStatePreparing();
        }
    }

    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
        if (mListener != null) {
            mListener.onStatePlaying();
        }
    }

    @Override
    public void onStatePause() {
        super.onStatePause();
        if (mListener != null) {
            mListener.onStatePause();
        }
    }

    @Override
    public void onStateError() {
        super.onStateError();
        if (mListener != null) {
            mListener.onStateError();
        }
    }

    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
        if (mListener != null) {
            mListener.onStateAutoComplete();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == cn.jzvd.R.id.fullscreen && mListener != null) {
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                //click quit fullscreen
                mListener.onQuitFullClick();
            } else {
                //click goto fullscreen
                mListener.onGoFullClick();
            }
        }
    }

    @Override
    public void startVideo() {
        super.startVideo();
        if (mListener != null) {
            mListener.startVideo();
        }
    }

    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
        if (mListener != null) {
            mListener.onInfo(what, extra);
        }
    }

    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
        if (mListener != null) {
            mListener.onError(what, extra);
        }
    }

    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
        if (mListener != null) {
            mListener.startWindowFullscreen();
        }
    }

    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
        if (mListener != null) {
            mListener.startWindowTiny();
        }
    }

}
