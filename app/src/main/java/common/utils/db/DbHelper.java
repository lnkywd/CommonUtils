package common.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import common.utils.database.DBManager;
import common.utils.database.MigrationHelper;
import common.utils.db.daos.DaoMaster;
import common.utils.db.daos.DaoSession;
import common.utils.db.daos.TestModelDao;
import common.utils.db.model.TestModel;

/**
 * @author wd
 * @date 2018/05/28
 * Email 18842602830@163.com
 * Description
 */

public class DbHelper {

    //数据库名称
    private static final String DB_NAME = "9da3d0ef-9a3a-b016-0df8-4a50bd1bb0e0.db";
    private static DbHelper instance;
    private DBManager<TestModel, Long> test;
    private MySQLiteOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private DbHelper() {

    }

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        init(context, DB_NAME);
    }

    public void init(Context context, String dbName) {
        mHelper = new MySQLiteOpenHelper(context, dbName, null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DBManager<TestModel, Long> test() {
        if (test == null) {
            test = new DBManager<TestModel, Long>() {
                @Override
                public AbstractDao<TestModel, Long> getAbstractDao() {
                    return mDaoSession.getTestModelDao();
                }
            };
        }
        return test;
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void close() {
        clear();
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
    }

    public void clear() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    private static class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {

                @Override
                public void onCreateAllTables(Database db, boolean ifNotExists) {
                    DaoMaster.createAllTables(db, ifNotExists);
                }

                @Override
                public void onDropAllTables(Database db, boolean ifExists) {
                    DaoMaster.dropAllTables(db, ifExists);
                }
            }, TestModelDao.class);
        }
    }

}
