package common.test;

import android.content.Context;
import android.util.AttributeSet;

import common.utils.view.CommonXEditText;

/**
 * @author wd
 * @date 2018/09/03
 * Email 18842602830@163.com
 * Description
 */

public class XEditText extends CommonXEditText {

    public XEditText(Context context) {
        super(context);
    }

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getDeleteRes() {
        return super.getDeleteRes();
    }

}
