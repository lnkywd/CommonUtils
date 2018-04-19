package common.utils.base.http;

/**
 * Created by MAC on 2017/3/28.
 */

public class ApiResponseWraper<T> extends BaseEntity {

    public T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
