package common.utils.view.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * CreateTime 2018/02/07
 * Email 18842602830@163.com
 * Description
 *
 * @author wd
 */

public class ScaleInTransformer implements ViewPager.PageTransformer {


    private static final float DEFAULT_MIN_SCALE = 0.9f;
    private float mMinScale = DEFAULT_MIN_SCALE;

    public static final float DEFAULT_CENTER = 0.5f;

    @Override
    public void transformPage(@NonNull View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        view.setPivotY(pageHeight / 2);
        view.setPivotX(pageWidth / 2);
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
            view.setPivotX(pageWidth);
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) //1-2:1[0,-1] ;2-1:1[-1,0]
            {

                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                view.setPivotX(pageWidth * (DEFAULT_CENTER + (DEFAULT_CENTER * -position)));

            } else //1-2:2[1,0] ;2-1:2[0,1]
            {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
                view.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }


        } else { // (1,+Infinity]
            view.setPivotX(0);
            view.setScaleX(mMinScale);
            view.setScaleY(mMinScale);
        }
    }
}
