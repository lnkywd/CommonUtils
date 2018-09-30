package common.utils.utils;

/**
 * @author wd
 * @date 2018/05/15
 * Email 18842602830@163.com
 * Description eventbus 传值
 */

public class FlagEvent<T> {

    private T data;

    private String key;

    private Object data1;

    public Object getData1() {
        return data1;
    }

    public void setData1(Object data1) {
        this.data1 = data1;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
