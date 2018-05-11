package common.utils.view;

import android.view.View;

/**
 * Created by Administrator on 2017/11/7 0007.
 */

public abstract class ViewClick implements View.OnClickListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastTime = 0;

    @Override
    public void onClick(View view) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastTime > MIN_CLICK_DELAY_TIME) {
            onViewClick(view);
            lastTime = curTime;
        }
    }

    public abstract void onViewClick(View view);
}
