package common.utils.utils.permission;

import android.content.Context;

import com.yanzhenjie.permission.Action;

import java.util.List;

/**
 * Created by fzz on 2017/9/1 0001.
 */

public abstract class CallBackPermission{
    private int code = 0;
    private Context context;
    private Action succeedAction;
    private Action failedAction;

    public void setCode(int code) {
        this.code = code;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getCode() {
        return code;
    }

    public Context getContext() {
        return context;
    }

    public CallBackPermission() {
        succeedAction = new Action() {
            @Override
            public void onAction(List<String> permissions) {
                succeed(permissions);
            }
        };
        failedAction = new Action() {
            @Override
            public void onAction(List<String> permissions) {
                failed(permissions);
            }
        };
    }

    /**
     * 成功的回调
     */
    protected abstract void succeed(List<String> permissions);

    /**
     * 失败的回调
     */
    protected abstract void failed(List<String> permissions);

    public Action getFailedAction() {
        return failedAction;
    }

    public Action getSucceedAction() {
        return succeedAction;
    }
}
