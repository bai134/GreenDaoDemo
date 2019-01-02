package com.bai.greendaodemo.application;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.bai.greendaodemo.db.CopyDatabase;
import com.bai.greendaodemo.db.gen.DaoMaster;
import com.bai.greendaodemo.db.gen.DaoSession;

public class GDApplication extends Application {

    private static GDApplication Instance;

    private DaoMaster MainMaster;
    private DaoMaster.DevOpenHelper devOpenHelper;
    private SQLiteDatabase db;
    private DaoSession MaindaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;

        InitDB();
    }

    private void InitDB(){
        //创建新的数据库
//        devOpenHelper = new DaoMaster.DevOpenHelper(this,"MAIN.db");
//        db = devOpenHelper.getWritableDatabase();

        //使用已有的数据库
        devOpenHelper = new DaoMaster.DevOpenHelper(this,"MAIN.db");
        CopyDatabase.initManager(getApplicationContext());
        CopyDatabase cp = CopyDatabase.getManager();
        db = cp.getDatabase("MAIN.db",devOpenHelper);
        MainMaster = new DaoMaster(db);
        MaindaoSession = MainMaster.newSession();
    }


    public DaoSession getMaindaoSession() {
        return MaindaoSession;
    }

    public static GDApplication getInstance(){
        return Instance;
    }
}
