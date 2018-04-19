package common.utils.utils;

import android.content.Context;

import common.utils.R;
import common.utils.view.ProgressDialog;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class ProgressDialogUtils {
    private static ProgressDialog progressDialog;

    public static void showDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context)
                    .withMsg("请稍后...")
                    .frameAnim(R.drawable.prg_anim_frame)
                    .outsideCancelable(false)
                    .cancelable(true);
        }

        progressDialog.show();
    }

    public static void dismissDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
