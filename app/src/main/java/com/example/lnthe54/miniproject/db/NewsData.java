package com.example.lnthe54.miniproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lnthe54.miniproject.model.News;

import java.util.ArrayList;

/**
 * @author lnthe54 on 9/9/2018
 * @project MiniProject
 */
public class NewsData {
    private SQLiteOpenHelper sqlOpenHelper;
    private SQLiteDatabase sqlDB;

    private String[] allColumns = {
            NewsSQLite.ID,
            NewsSQLite.TITLE,
            NewsSQLite.DESC,
            NewsSQLite.LINK,
            NewsSQLite.IMAGE,
            NewsSQLite.PUB_DATE
    };

    private Context context;

    public NewsData(Context context) {
        this.sqlOpenHelper = new NewsSQLite(context);
        this.context = context;
    }

    public void open() throws SQLiteException {
        sqlDB = sqlOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException {
        sqlOpenHelper.close();
    }

    public void addNews(String title, String desc, String link, String image, String pubDate) {
        ContentValues values = new ContentValues();
        values.put(NewsSQLite.TITLE, title);
        values.put(NewsSQLite.DESC, desc);
        values.put(NewsSQLite.IMAGE, image);
        values.put(NewsSQLite.PUB_DATE, pubDate);
        values.put(NewsSQLite.LINK, link);

        sqlDB.insert(NewsSQLite.TABLE_NAME, null, values);
    }

    public void delNews(int id) {
        sqlDB.delete(NewsSQLite.TABLE_NAME, NewsSQLite.ID + "=" + id, null);
    }

    public ArrayList<News> getNews() {
        ArrayList<News> listNote = new ArrayList<>();
        Cursor cursor = sqlDB.query(NewsSQLite.TABLE_NAME, allColumns,
                null, null, null, null, null);
        if (cursor == null) {
            return null;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            News news = cursorToNews(cursor);
            listNote.add(news);
            cursor.moveToNext();
        }
        return listNote;
    }

    private News cursorToNews(Cursor cursor) {
        News news = new News();
        news.setId(cursor.getInt(0));
        news.setTitle(cursor.getString(1));
        news.setDesc(cursor.getString(2));
        news.setLink(cursor.getString(3));
        news.setImage(cursor.getString(4));
        news.setPubDate(cursor.getString(5));
        return news;
    }
}
