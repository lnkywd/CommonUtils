package common.utils.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.Request;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

import common.utils.utils.ShowUtil;

/**
 * Created by fzz on 2017/9/1 0001.
 */

public class PermissionController implements PermissionRequest {
    private CallBackPermission callBackPermission;
    private Request request;
    private Context context;


    PermissionController(Context context){
        if (context==null){
            throw new IllegalArgumentException("The Context can not be null.");
        }
        initUtils(context);
    }

    PermissionController(Activity activity){
        if (activity==null){
            throw new IllegalArgumentException("The Activity can not be null.");
        }
        initUtils(activity);
    }

    PermissionController(Fragment context){
        if (context==null){
            throw new IllegalArgumentException("The Fragment can not be null.");
        }
        initUtils(context);
    }

    /**
     * 初始化方法
     * @param context context {@link Context}.
     * @return PermissionRequest
     */
    @NonNull
    private PermissionRequest initUtils(Context context) {
        this.context = context;
        request = AndPermission.with(context);
        return this;
    }

    /**
     *
     * @param activity activity {@link Activity}.
     * @return PermissionRequest
     */
    @NonNull
    private PermissionRequest initUtils(Activity activity) {
        context = activity;
        request = AndPermission.with(activity);
        return this;
    }

    /**
     *
     * @param fragment activity {@link Fragment}.
     *
     * @return PermissionRequest
     */
    @NonNull
    private PermissionRequest initUtils(Fragment fragment) {
        this.context = fragment.getActivity();
        request = AndPermission.with(fragment);
        return this;
    }

    @NonNull
    @Override
    public PermissionRequest CallBackPermission(CallBackPermission callBack) {
        callBackPermission = callBack;
        request.onGranted(callBackPermission.getSucceedAction())
                .onDenied(callBackPermission.getFailedAction());
        return this;
    }

    @NonNull
    @Override
    public PermissionRequest addPermissions(String... permission) {
        request.permission(permission);
        return this;
    }
    @NonNull
    @Override
    public PermissionRequest addPermission(String permission) {
        request.permission(permission);
        return this;
    }
    @NonNull
    @Override
    public PermissionRequest popInit(final Context context) {
        request.rationale(new Rationale() {
            @Override
            public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
                ShowUtil.showToast(context,"继续进行授权");
                executor.execute();
            }
        });
        return this;
    }

    public void permissionStart() {
        request.start();
    }

}
