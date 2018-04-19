package common.utils.utils.permission;

import android.content.Context;
import android.support.annotation.NonNull;


/**
 * Created by fzz on 2017/9/1 0001.
 */

public interface PermissionRequest {
    /**
     * 请求回调
     *
     * @param callBackPermission callBackPermission {@Link CallBackPermission }
     * @return PermissionRequest
     */
    @NonNull
    PermissionRequest CallBackPermission(CallBackPermission callBackPermission);

    /**
     * 添加权限 可以是一个 可以是多个
     * @param permission 一个或更多个权限
     * @return PermissionRequest
     */
    @NonNull
    PermissionRequest addPermissions(String... permission);

    /**
     * 添加一个权限
     * @param permission 一个权限
     * @return PermissionRequest
     */
    @NonNull
    PermissionRequest addPermission(String permission);

    /**
     * 设置弹窗
     * @param context context {@link Context}.
     * @return
     */
    @NonNull
    PermissionRequest popInit(final Context context);

    void permissionStart();
}
