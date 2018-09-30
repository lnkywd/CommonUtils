package common.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.ImageUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.io.File;
import java.util.List;

import common.test.db.DbHelper;
import common.test.db.model.TestModel;
import common.utils.R;
import common.utils.base.activity.CommonBaseBackActivity;
import common.utils.databinding.ActivityMainBinding;
import common.utils.utils.RxTransformer;
import common.utils.utils.ToastUtils;
import common.utils.utils.apkdownload.UpdateAppUtils;
import io.reactivex.functions.Consumer;

/**
 * @author wd
 * @date 2018/04/20
 * Email 18842602830@163.com
 * Description
 */

public class MainActivity extends CommonBaseBackActivity {

    private ActivityMainBinding binding;

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected boolean showTopBlackFont() {
        return false;
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn01:
                Rect rect = new Rect();
                view.getGlobalVisibleRect(rect);

                TestActivity.launch(this, rect);
                overridePendingTransition(0, 0);
                break;
            case R.id.btn02:
                UpdateAppUtils.from(this)
                        .serverVersionName("2.0.0")
                        .checkBy(UpdateAppUtils.CHECK_BY_OUT)
                        .isForce(true)
                        .apkPath("http://imtt.dd.qq.com/16891/AA179694CC535CE01320B7963446FEED.apk?fsname=com.qiyi.video_9.5.0_81070.apk&csr=1bbd")
                        .update();

                break;
            case R.id.btn03:
                Bundle bundle = new Bundle();
                bundle.putString("1", "qwe");
                bundle.putInt("2", 22);
                bundle.putBoolean("3", false);
                ARouter.getInstance().build("/test/jump1")
                        .with(bundle)
                        .navigation(this, new NavigationCallback() {
                            @Override
                            public void onFound(Postcard postcard) {
                                Log.i("wdwdwd", "onFound执行了==");
                            }

                            @Override
                            public void onLost(Postcard postcard) {
                                Log.i("wdwdwd", "onLost执行了==");
                            }

                            @Override
                            public void onArrival(Postcard postcard) {
                                Log.i("wdwdwd", "onArrival执行了==");
                            }

                            @Override
                            public void onInterrupt(Postcard postcard) {
                                Log.i("wdwdwd", "onInterrupt执行了==");
                            }
                        });
                break;
            case R.id.btn04:
                JumpActivity1.launch(mContext);
                break;
            case R.id.btn05:
                Test5Activity.launch(mContext);
                break;
            case R.id.btn06:
                for (int i = 0; i < 10; i++) {
                    DbHelper.getInstance().test().insertOrReplace(new TestModel((long) i, "张" + i, i, i))
                            .compose(RxTransformer.<Boolean>switchSchedulers())
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean aBoolean) throws Exception {
                                }
                            });
                }
                break;
            case R.id.btn07:
                DbHelper.getInstance().test().load(3L)
                        .subscribe(new Consumer<TestModel>() {
                            @Override
                            public void accept(TestModel testModel) throws Exception {
                                if (testModel != null) {
                                    ToastUtils.showShort(testModel.toString());
                                } else {
                                    ToastUtils.showShort("数据为空");
                                }
                            }
                        });
                break;
            case R.id.btn08:
                Test6Activity.launch(mContext);
                break;
            case R.id.btn09:
                Test7Activity.launch(mContext);

                break;
            case R.id.btn12:
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Pictures" + File.separator;
                Log.i("wdwdwd", filePath);
                final String fileName = String.format("%s%s.png", filePath, System.currentTimeMillis());
                AndPermission.with(mContext)
                        .runtime()
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .onGranted(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {
                                Glide.with(mContext)
                                        .load("https://img.globalwinner.cn/upload/cb97e32ef5a9ebfed76d3738fb95298c.png")
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();
                                                ImageUtils.save(bitmap, fileName, Bitmap.CompressFormat.PNG, true);
                                                Uri uri = Uri.fromFile(new File(fileName));
                                                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

//                                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "分享图片", "分享图片");
//                                                Log.i("wdwdwdwd", "执行了===");
                                            }
                                        });
                            }
                        })
                        .onDenied(new Action<List<String>>() {
                            @Override
                            public void onAction(List<String> data) {

                            }
                        })
                        .start();

                break;
            default:
        }
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @NonNull
    @Override
    public View bindLayout() {
        binding = getDataBinding(R.layout.activity_main);
        return binding.getRoot();
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public int setStatusBarColor() {
        return R.color.color_333333;
    }

    @Override
    public boolean isShowStatusBar() {
        return true;
    }

    @Override
    protected View getTitleView() {
        return null;
    }
}
