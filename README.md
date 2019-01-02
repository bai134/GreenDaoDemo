# GreenDaoDemo
GreenDao使用已有的数据库及简单解析
要想使用GreenDao，就必须要添加GreenDao依赖

```
implementation 'org.greenrobot:greendao:3.2.2'

apply plugin: 'org.greenrobot.greendao'

classpath 'org.greenrobot:greendao-gradle-plugin:3.2.0'

greendao {
    schemaVersion 1
    daoPackage 'com.bai.greendaodemo.db.gen'
    targetGenDir 'src/main/java'
}
```
因为是使用已有的数据库，那么创建实体类的时候要加上`createInDb = false`这个属性且实体类参数要与表字段相对应，写好实体类后Build--->Make Project，成功后就会创建实体类所对应的Dao，以及DaoMaster和DaoSession这两个类，这些类就会出现在db.gen这个文件夹下

使用已有的数据库，就是在第一次启动的时候把所需的数据复制到 /data/data/包名/databases路径下
使用的是这位大佬所封装的方法，[详细方法请看这里](https://blog.csdn.net/trbbadboy/article/details/8001157)
感谢大佬造的轮子，当然有一些符合自己项目的改变

GreenDao使用的是SQLiteOpenHelper创建数据库，所创建的数据库在/data/data/包名/databases路径下，所以直接把已有的数据库复制到该路径下

而GreenDao创建表的时候是在这里

```
public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);//在这里创建表
        }
    }
```
追溯上去最终是在SQLiteOpenHelper这个类中的    private SQLiteDatabase getDatabaseLocked(boolean writable) 这个方法中被调用

```
try {
                        if (version == 0) {
                            onCreate(db);//在这里调用
                        } else {
                            if (version > mNewVersion) {
                                onDowngrade(db, version, mNewVersion);
                            } else {
                                onUpgrade(db, version, mNewVersion);
                            }
                        }
                        db.setVersion(mNewVersion);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
```
而getDatabaseLocked这个方法会在这两个方法中调用

```
public SQLiteDatabase getReadableDatabase() {
        synchronized (this) {
            return getDatabaseLocked(false);
        }
    }
public SQLiteDatabase getWritableDatabase() {
        synchronized (this) {
            return getDatabaseLocked(true);
        }
    }
```
这个构造方法定义的 是数据库的一些信息，所以New SQLiteOpenHelper()时并不会创建数据库，只是赋值一些参数，只有再调用getReadableDatabase()和getWritableDatabase()这两个方法时才会去创建数据，获取到数据库操作对象
```
public SQLiteOpenHelper(Context context, String name, CursorFactory factory, int version,
            int minimumSupportedVersion, DatabaseErrorHandler errorHandler)
```
在GreenDao中有对SQLiteOpenHelper的继承

```
 public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }
```
DaoMaster中的static class DevOPenHelper 继承OpenHelper，追溯上去会发现继承的是SQLiteOpenHelper类，所以使用
`devOpenHelper = new DaoMaster.DevOpenHelper(this,"MAIN.db");`时并不会创建数据库，
`db = devOpenHelper.getWritableDatabase();`调用这个方法时才会创建数据库

明白这个建库，建表的方法后，就会对刚才提到的那位大佬造的轮子进行更改

```
public SQLiteDatabase getDatabase(String dbfile, DaoMaster.DevOpenHelper devOpenHelper) {
        if(context==null)
            return null;
        String spath = getDatabaseFilepath();
        String sfile = getDatabaseFile(dbfile);
        File file = new File(sfile);
        SharedPreferences dbs = context.getSharedPreferences(CopyDatabase.class.toString(), 0);
        boolean flag = dbs.getBoolean(dbfile, false);
        if(!flag || !file.exists()){
            file = new File(spath);
            if(!file.exists() && !file.mkdirs()){
                Log.i(tag, "Create \""+spath+"\" fail!");
                return null;
            }
            if(!copyAssetsToFilesystem(dbfile, sfile)){
                Log.i(tag, String.format("Copy %s to %s fail!", dbfile, sfile));
                return null;
            }
            dbs.edit().putBoolean(dbfile, true).commit();
        }
        return devOpenHelper.getWritableDatabase();
    }
```
主要是更改这个方法，使其返回的是DaoMaster.DevOpenHelper所创建的数据库操作对象

使用时为这样
```
devOpenHelper = new DaoMaster.DevOpenHelper(this,"MAIN.db");
CopyDatabase.initManager(getApplicationContext());
CopyDatabase cp = CopyDatabase.getManager();
db = cp.getDatabase("MAIN.db",devOpenHelper);
MainMaster = new DaoMaster(db);
MaindaoSession = MainMaster.newSession();
```
之后就能正常的使用了。

具体的一些使用方法请看[这位大佬的这篇博客](https://blog.csdn.net/speedystone/article/details/72769793)

