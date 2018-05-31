package common.utils.database;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author wd
 * @date 2018/05/28
 * Email 18842602830@163.com
 * Description 数据库操作接口
 */

public interface IDataBase<M, K> {

    Observable<Boolean> insert(@NotNull M m);

    Observable<Boolean> insertOrReplace(@NotNull M m);

    Observable<Boolean> insertInTx(@NotNull List<M> list);

    Observable<Boolean> insertOrReplaceInTx(@NotNull List<M> list);

    Observable<Boolean> delete(@NotNull M m);

    Observable<Boolean> deleteByKey(@NotNull K key);

    Observable<Boolean> deleteInTx(@NotNull List<M> list);

    Observable<Boolean> deleteByKeyInTx(@NotNull K... key);

    Observable<Boolean> deleteAll();

    Observable<Boolean> update(@NotNull M m);

    Observable<Boolean> updateInTx(@NotNull M... m);

    Observable<Boolean> updateInTx(@NotNull List<M> list);

    Observable<M> load(@NotNull K key);

    Observable<List<M>> loadAll();

    Observable<Boolean> refresh(@NotNull M m);

    void runInTx(@NotNull Runnable runnable);

    QueryBuilder<M> queryBuilder();

    Observable<List<M>> queryRaw(@NotNull String where, @NotNull String... selectionArg);

    Observable<Query<M>> queryRawCreate(@NotNull String where, @NotNull Object... selectionArg);

    Observable<Query<M>> queryRawCreateListArgs(@NotNull String where, @NotNull Collection<Object> selectionArg);

    AbstractDao<M, K> getAbstractDao();

}
