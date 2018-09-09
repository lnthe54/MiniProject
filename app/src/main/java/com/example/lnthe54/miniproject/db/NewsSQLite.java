package com.example.lnthe54.miniproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author lnthe54 on 9/9/2018
 * @project MiniProject
 */
public class NewsSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "news.db";
    public static final String TABLE_NAME = "news";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESC = "description";
    public static final String LINK = "link";
    public static final String IMAGE = "image";
    public static final String PUB_DATE = "pubDate";

    public static final int VERSION = 1;

    public static String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + "( "
            + ID + " INTEGER PRIMARY KEY, "
            + TITLE + " TEXT NOT NULL, "
            + DESC + " TEXT NOT NULL, "
            + LINK + " TEXT NOT NULL, "
            + IMAGE + " TEXT NOT NULL, "
            + PUB_DATE + " TEXT NOT NULL);";

    public NewsSQLite(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
