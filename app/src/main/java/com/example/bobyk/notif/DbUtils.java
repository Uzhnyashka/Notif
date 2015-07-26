package com.example.bobyk.notif;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by bobyk on 26/07/15.
 */
public class DbUtils {

    public DbNotification dbNotification;
    public Cursor c;
    private ArrayList<NotificationItem> notificationItems;

    public DbUtils(Context _context){
        dbNotification = new DbNotification(_context);
        SQLiteDatabase db = dbNotification.getWritableDatabase();
        String select = "SELECT * FROM " + ConstValues.NOTIFICATION_TABLE;
        c = db.rawQuery(select, null);
    }

    public JSONObject bundleToJson(Bundle _bundle) {
        JSONObject json = new JSONObject();
        Set<String> k = _bundle.keySet();
        for (String key : k) {
            try {
                json.put(key, _bundle.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return json;
    }

    public void addJsonToDB(JSONObject _jsonObject){
        String jsonString = _jsonObject.toString();
        SQLiteDatabase db = dbNotification.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ConstValues.KEY_JSON_STRING, jsonString);
        db.insert(ConstValues.NOTIFICATION_TABLE, null, cv);
    }

    public ArrayList<NotificationItem> getNotificationsFromDb() throws JSONException {
        notificationItems = new ArrayList<>();

        if (c.moveToFirst()){
            int jsonColIndex = c.getColumnIndex(ConstValues.KEY_JSON_STRING);

            do {
                NotificationItem ni = new NotificationItem();
                String jsonString = c.getString(jsonColIndex);
                JSONObject jsonObject = new JSONObject(jsonString);
                ni.setMessage(jsonObject.getString("message"));
                ni.setTitle(jsonObject.getString("title"));
                ni.setSubtitle(jsonObject.getString("subtitle"));
                ni.setTickerText(jsonObject.getString("tickerText"));
                ni.setVibrate(jsonObject.getInt("vibrate"));
                ni.setSound(jsonObject.getInt("sound"));

                notificationItems.add(ni);
            }while(c.moveToNext());
        }

        return notificationItems;
    }

}
