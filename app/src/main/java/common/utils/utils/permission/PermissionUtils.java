package common.utils.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by fzz on 2017/9/1 0001.
 */

public class PermissionUtils {

    public static
    @NonNull
    PermissionRequest init(@NonNull Context context) {

        return new PermissionController(context);
    }

    public static
    @NonNull
    PermissionRequest init(@NonNull Activity context) {

        return new PermissionController(context);
    }

    public static
    @NonNull
    PermissionRequest init(@NonNull Fragment context) {

        return new PermissionController(context);
    }
}
