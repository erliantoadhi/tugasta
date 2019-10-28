package com.example.drawerlayout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        super(context, DATABASE_NAME, null, 2);
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

    public boolean isi(int seri, String name, int umur, String nmIbu, String noHp, int tb, float bb, String gizi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CL_NOSERI, seri);
        cv.put(CL_NMBAYI, name);
        cv.put(CL_UMURBAYI, umur);
        cv.put(CL_NMIBU, nmIbu);
        cv.put(CL_NOHP, noHp);
        cv.put(CL_TB, tb);
        cv.put(CL_BB, bb);
        cv.put(CL_GIZI, gizi);
        long result = db.insert(bayi, null, cv);

        if (result == -1) return false;
        else return true;
    }

    public Cursor getData(String nmby, String nmIbu) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi + " WHERE " + CL_NMBAYI + "='" + nmby + "' AND "+ CL_NMIBU + "='" + nmIbu + "'";
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + bayi ;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }
}
