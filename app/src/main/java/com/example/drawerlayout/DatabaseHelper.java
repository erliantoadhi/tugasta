package com.example.drawerlayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "posyandu.db";

    public static final String bayi = "bayi";

    public static final String CL_ID = "id";
    public static final String CL_NOSERI = "noSeri";
    public static final String CL_NMBAYI = "nmBayi";
    public static final String CL_UMURBAYI = "umurBayi";
    public static final String CL_NMIBU = "nmIbu";
    public static final String CL_NOHP = "noHp";
    public static final String CL_TB = "tb";
    public static final String CL_BB = "bb";
    public static final String CL_GIZI = "gizi";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + bayi + " (" +
                CL_ID + " INTEGER PRIMARY KEY autoincrement, " +
                CL_NOSERI + " INTEGER NOT NULL, " +
                CL_NMBAYI + " TEXT NOT NULL, " +
                CL_UMURBAYI + " INTEGER NOT NULL, " +
                CL_NMIBU + " TEXT NOT NULL, " +
                CL_NOHP + " TEXT NOT NULL, " +
                CL_TB + " INTEGER NOT NULL, " +
                CL_BB + " FLOAT NOT NULL, " +
                CL_GIZI + " TEXT NOT NULL)";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + bayi);
        onCreate(db);
    }

    public boolean isi(long noSeri, String name, int umur, String nmIbu, String noHp, int tb, float bb, String gizi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CL_NOSERI, noSeri);
        cv.put(CL_NMBAYI, name);
        cv.put(CL_UMURBAYI, umur);
        cv.put(CL_NMIBU, nmIbu);
        cv.put(CL_NOHP, noHp);
        cv.put(CL_TB, tb);
        cv.put(CL_BB, bb);
        cv.put(CL_GIZI, gizi);

        Log.d("dbhelper", "db noSeri = " + noSeri);

        if(getAllNoSeri().contains(String.valueOf(noSeri))) {
            db.update(bayi, cv, CL_NOSERI + "=?", new String[] {String.valueOf(noSeri)});
            Log.d("dbhelper", "db = update");
        } else {
            db.insert(bayi, null, cv );
            Log.d("dbhelper", "db = insert");
        }

        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi ;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public ArrayList<String> getAllNoSeri(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi ;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String > noSeri = new ArrayList();
        if (cursor != null){
            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()) {
                    noSeri.add(cursor.getString(1));
                    cursor.moveToNext();
                }
            }
        }

        return noSeri;
    }

    public String getNamaBayi(String noSeri) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi + " WHERE " + CL_NOSERI + "='" + noSeri +"'";
        Cursor cursor = db.rawQuery(query, null);

        String nama = "";

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    nama = (cursor.getString(2));
                } while(cursor.moveToNext());
            }
        }

        return nama;
    }

    public String getUmurBayi(String noSeri) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi + " WHERE " + CL_NOSERI + "='" + noSeri +"'";
        Cursor cursor = db.rawQuery(query, null);
        String nama = "";

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    nama = (cursor.getString(3));
                } while(cursor.moveToNext());
            }
        }

        return nama;
    }

    public String getIbuBayi(String noSeri) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi + " WHERE " + CL_NOSERI + "='" + noSeri +"'";
        Cursor cursor = db.rawQuery(query, null);
        String nama = "";

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    nama = (cursor.getString(4));
                } while(cursor.moveToNext());
            }
        }

        return nama;
    }

    public String getNoHP(String noSeri) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi + " WHERE " + CL_NOSERI + "='" + noSeri +"'";
        Cursor cursor = db.rawQuery(query, null);
        String nama = "";

        if (cursor != null){
            if (cursor.moveToFirst()) {
                do {
                    nama = (cursor.getString(5));
                } while(cursor.moveToNext());
            }
        }

        return nama;
    }
}
