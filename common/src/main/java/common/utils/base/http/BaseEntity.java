package common.utils.base.http;

import android.text.TextUtils;

/**
 * Created by MAC on 2017/3/28.
 * <p>
 * 数据基础类型
 */

public class BaseEntity {

    public String message;

    public String code;

    public boolean isSuccess() {
        return TextUtils.equals("200", getCode());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
