package common.utils.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wd on 2017/7/26.
 * rxjava 线程操作工具类
 */

public class RxTransformer {

    public static <T> ObservableTransformer<T, T> switchSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
//                return observable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        // dispose()方法只会切断上游和下游的数据传递，并不会将 actual 置为 null
//                        // onTerminateDetach() 方法只是将自己的 actual 对象置为空,而其他上游的 actual 对象没有为空
//                        // 例如 map 等操作符后，再次调用 onTerminateDetach()
//                        .onTerminateDetach();
                /**
                 * onTerminateDetach()方法只是将自己的actual对象置为空
                 * ,而其他上游的actual对象没有为空,可能还会出现内存泄露风险,将代码改为下面:
                 */
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onTerminateDetach()
                        .map(new Function<T, T>() {
                            @Override
                            public T apply(T t) throws Exception {
                                return t;
                            }
                        })
                        .onTerminateDetach();
            }
        };
    }

    public static <T> ObservableTransformer<T, T> switchSchedulers(final OnLoadingListener listener) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onTerminateDetach()
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                listener.onSubscribe(disposable);
                            }
                        })
                        .doOnTerminate(new Action() {
                            @Override
                            public void run() throws Exception {
                                listener.onTerminate();
                            }
                        });
            }
        };
    }

    public interface OnLoadingListener {

        void onSubscribe(Disposable disposable);

        void onTerminate();
    }


}
