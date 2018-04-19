package common.utils.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import common.utils.R;
import common.utils.databinding.ViewHomeTabBottomBinding;


public class HomeTabBottomView extends LinearLayout {

    private ViewHomeTabBottomBinding mBinding;

    private int mOldPosition = 0;
    private OnMenuTabClickListener mOnMenuTabClickListener;

    public HomeTabBottomView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_home_tab_bottom, this, true);
    }

    public HomeTabBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HomeTabBottomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnMenuTabClickListener(OnMenuTabClickListener onMenuTabClickListener) {
        mOnMenuTabClickListener = onMenuTabClickListener;
    }

    public void setClick() {
        View[] views = new View[]{};

        for (int i = 0; i < views.length; i++) {
            views[i].setTag(i);
            views[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = Integer.parseInt(v.getTag().toString());
                    if (mOnMenuTabClickListener != null) {
                        if (mOldPosition == position) {
                            mOnMenuTabClickListener.onMenuTabReSelected(position);
                        } else {
                            mOnMenuTabClickListener.onMenuTabSelected(position);
                        }
                    }
                    if (position != mOldPosition) {
                        mOldPosition = position;
                        setTabSelect(position);
                    }
                }
            });
        }
    }

    public void setTabSelect(int position) {
        mOldPosition = position;
        View[] views = new View[]{};
        for (int i = 0; i < views.length; i++) {
            views[i].setSelected(i == position);
        }
    }

    public interface OnMenuTabClickListener {
        void onMenuTabSelected(int position);

        void onMenuTabReSelected(int position);
    }

}
