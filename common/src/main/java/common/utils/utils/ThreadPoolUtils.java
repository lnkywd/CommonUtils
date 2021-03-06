package common.utils.utils;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wd
 * @date 2018/04/28
 * Email 18842602830@163.com
 * Description
 */

public class ThreadPoolUtils {

    //在创建线程执行
    private static Handler mhandler = new Handler();
    private static ExecutorService executorService;

    public static ExecutorService fixThread(int poolSize) {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(poolSize, poolSize, 5L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        }
        return executorService;
    }

    public static ExecutorService fixThread() {
        return fixThread(5);
    }

    public static void fixThread(final Runnable runnable, long delayMillis) {
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fixThread().execute(runnable);
            }
        }, delayMillis);
    }

}
