package common.utils.base.http;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MAC on 2017/3/28.
 */

public class ApiResponseListWraper<T> extends BaseEntity {

    public List<T> data;

    public List<T> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
