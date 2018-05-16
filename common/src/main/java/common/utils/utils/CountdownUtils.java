package common.utils.utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author wd
 * @date 2018/04/18
 * Email 18842602830@163.com
 * Description 倒计时工具类
 */

public class CountdownUtils {

    private static final AtomicReference<CountdownUtils> INSTANCE = new AtomicReference<>();
    private Disposable mDisposable;

    private CountdownUtils() {
    }

    public static CountdownUtils getInstance() {
        for (; ; ) {
            CountdownUtils current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new CountdownUtils();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }

    public void countDown(long time, final OnTimeOverListener listener) {
        Observable.timer(time, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable disposable) {
                        mDisposable = disposable;
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Long number) {
                        if (mDisposable == null || mDisposable.isDisposed()) {
                            return;
                        }
                        listener.onNext();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        if (mDisposable != null && !mDisposable.isDisposed()) {
                            mDisposable.dispose();
                        }
                    }
                });
    }

    public void onDestory() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public interface OnTimeOverListener {
        void onNext();
    }


}
