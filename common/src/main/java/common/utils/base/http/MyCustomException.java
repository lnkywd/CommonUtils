package common.utils.base.http;

/**
 * @author wd
 * @date 2018/09/07
 * Email 18842602830@163.com
 * Description
 */

public class MyCustomException extends Exception {

    public static final int ENTITY_ERROR = 1;
    public static final int CODE_ERROR = 2;
    public static final int RX_ERROR = 3;


    private int mErrorType;

    public MyCustomException(int errorType) {
        mErrorType = errorType;
    }

    public MyCustomException(String message, int errorType) {
        super(message);
        mErrorType = errorType;
    }

    public MyCustomException(String message, Throwable cause, int errorType) {
        super(message, cause);
        mErrorType = errorType;
    }

    public MyCustomException(Throwable cause, int errorType) {
        super(cause);
        mErrorType = errorType;
    }

    public int getErrorType() {
        return mErrorType;
    }
}
