package common.utils.database;

import android.database.sqlite.SQLiteException;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import common.utils.utils.LogUtils;
import io.reactivex.Observable;

/**
 * @author wd
 * @date 2018/05/28
 * Email 18842602830@163.com
 * Description 数据库操作管理
 */

public abstract class DBManager<M, K> implements IDataBase<M, K> {

    @Override
    public Observable<Boolean> insert(final M m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().insert(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertOrReplace(final M m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().insertOrReplace(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertInTx(final List<M> list) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().insertInTx(list);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> insertOrReplaceInTx(final List<M> list) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().insertOrReplaceInTx(list);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> delete(final M m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().delete(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteByKey(final K key) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().deleteByKey(key);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteInTx(final List<M> list) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().deleteInTx(list);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteByKeyInTx(final K[] key) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().deleteByKeyInTx(key);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAll() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().deleteAll();
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> update(final M m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().update(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateInTx(final M[] m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().updateInTx(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> updateInTx(final List<M> list) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().updateInTx(list);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public Observable<M> load(final K key) {
        return Observable.fromCallable(new Callable<M>() {
            @Override
            public M call() throws Exception {
                return getAbstractDao().load(key);
            }
        });
    }

    @Override
    public Observable<List<M>> loadAll() {
        return Observable.fromCallable(new Callable<List<M>>() {
            @Override
            public List<M> call() throws Exception {
                return getAbstractDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> refresh(final M m) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    getAbstractDao().refresh(m);
                } catch (SQLiteException e) {
                    LogUtils.e(e);
                    return false;
                }
                return true;
            }
        });
    }

    @Override
    public void runInTx(Runnable runnable) {
        try {
            getAbstractDao().getSession().runInTx(runnable);
        } catch (SQLiteException e) {
            LogUtils.e(e);
        }
    }

    @Override
    public QueryBuilder<M> queryBuilder() {
        return getAbstractDao().queryBuilder();
    }

    @Override
    public Observable<List<M>> queryRaw(final String where, final String... selectionArg) {
        return Observable.fromCallable(new Callable<List<M>>() {
            @Override
            public List<M> call() throws Exception {
                return getAbstractDao().queryRaw(where, selectionArg);
            }
        });
    }

    @Override
    public Observable<Query<M>> queryRawCreate(final String where, final Object... selectionArg) {
        return Observable.fromCallable(new Callable<Query<M>>() {
            @Override
            public Query<M> call() throws Exception {
                return getAbstractDao().queryRawCreate(where, selectionArg);
            }
        });
    }

    @Override
    public Observable<Query<M>> queryRawCreateListArgs(final String where, final Collection<Object> selectionArg) {
        return Observable.fromCallable(new Callable<Query<M>>() {
            @Override
            public Query<M> call() throws Exception {
                return getAbstractDao().queryRawCreateListArgs(where, selectionArg);
            }
        });
    }

    @Override
    public abstract AbstractDao<M, K> getAbstractDao();

}
