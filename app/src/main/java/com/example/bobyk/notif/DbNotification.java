package com.example.bobyk.notif;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bobyk on 26/07/15.
 */
public class DbNotification extends SQLiteOpenHelper {


    public DbNotification(Context context) {
        super(context, ConstValues.DB_NAME, null, ConstValues.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + ConstValues.NOTIFICATION_TABLE + " ("
                + ConstValues.KEY_ID + " integer primary key autoincrement, "
                + ConstValues.KEY_JSON_STRING + " text);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
