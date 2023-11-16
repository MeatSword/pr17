package com.example.pr_17;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        // конструкторсуперкласса
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {

        Log.d("tag", "--- onCreate database ---");
        // создаемтаблицусполями
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "animal text,"
                + "name text,"
                + "size integer,"
                + "weight integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

