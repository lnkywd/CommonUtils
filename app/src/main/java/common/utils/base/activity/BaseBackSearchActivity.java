package common.utils.base.activity;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

import common.utils.R;
import common.utils.databinding.ActivityBaseSearchActivityBinding;

/**
 * Created by fzz on 2018/3/19 0019.
 */

public abstract class BaseBackSearchActivity extends BaseActivity {
    ActivityBaseSearchActivityBinding baseBinding;

    protected abstract void search(String searchText);

    @Override
    protected void setLayout(View view) {
        SlidrConfig.Builder mBuilder = new SlidrConfig.Builder()
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .edge(true)
                .velocityThreshold(5f)
                .distanceThreshold(.35f);
        Slidr.attach(this, mBuilder.build());
        baseBinding = getDataBinding(R.layout.activity_base_search_activity);
        setContentView(baseBinding.getRoot());
        contentView = baseBinding.getRoot();
        setSupportActionBar(baseBinding.toolbar);
        initStatusBar(setStatusBarColor(), isShowStatusBar());

        baseBinding.toolbar.setNavigationIcon(R.mipmap.icon_train_register_back);

        getToolBar().setDisplayShowTitleEnabled(false);
//        int height = unDisplayViewSize(baseBinding.abl)[1];
//        view.setPadding(0,0,0,height);
        baseBinding.activityContainer.addView(view);
        baseBinding.barEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.getText().toString().trim());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    // 隐藏软键盘
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected ActionBar getToolBar() {
        return getSupportActionBar();
    }

    protected void setSearchHint(String searchHint){
        baseBinding.barEtSearch.setHint(searchHint);
    }

    protected void setLiftBackBackground(@DrawableRes int id) {
        baseBinding.toolbar.setNavigationIcon(id);
    }

    /**
     * 计算一个view的宽高
     *
     * @param view 需要计算的view
     * @return 0 宽 、1 高
     */
    private int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }
}
