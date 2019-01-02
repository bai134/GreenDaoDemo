package com.bai.greendaodemo.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bai.greendaodemo.db.gen.DaoMaster;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDatabase {

    private static String tag = "CopyDatabase";
    private static String databasepath = "/data/data/%s/databases";
    private Context context;
    private static CopyDatabase mInstance = null;

    public static void initManager(Context context){
        if(mInstance == null){
            mInstance = new CopyDatabase(context);
        }
    }

    private CopyDatabase(Context context){
        this.context = context;
    }

    public static CopyDatabase getManager(){
        return mInstance;
    }

    private boolean copyAssetsToFilesystem(String assetsSrc, String des){
        InputStream istream = null;
        OutputStream ostream = null;
        try{
            AssetManager am = context.getAssets();
            istream = am.open(assetsSrc);
            ostream = new FileOutputStream(des);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = istream.read(buffer))>0){
                ostream.write(buffer, 0, length);
            }
            istream.close();
            ostream.close();
        }
        catch(Exception e){
            e.printStackTrace();
            try{
                if(istream!=null)
                    istream.close();
                if(ostream!=null)
                    ostream.close();
            }
            catch(Exception ee){
                ee.printStackTrace();
            }
            return false;
        }
        return true;
    }

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
    private String getDatabaseFilepath(){
        return String.format(databasepath, context.getApplicationInfo().packageName);
    }
    private String getDatabaseFile(String dbfile){
        return getDatabaseFilepath()+"/"+dbfile;
    }
}
