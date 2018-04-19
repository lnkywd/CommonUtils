package common.utils.utils.recycler;

import android.view.View;

/**
 * @author wd
 * @date 2018/04/04
 * Email 18842602830@163.com
 * Description
 */

public interface StickyView {

    /**
     * 是否是吸附view
     *
     * @param view
     * @return
     */
    boolean isStickyView(View view);

    /**
     * 得到吸附view的itemType
     *
     * @return
     */
    int getStickViewType();
}
