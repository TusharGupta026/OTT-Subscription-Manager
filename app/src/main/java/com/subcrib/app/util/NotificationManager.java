package com.subcrib.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NotificationManager extends SQLiteOpenHelper {

    private static final String dbname="dbnotification";


    public NotificationManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table notifications (notificationId integer, notification text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query="DROP TABLE IF EXISTS subscriptions";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public String addNotification(int notificationId, String notification){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("notificationId",notificationId);
        cv.put("notification",notification);


        float res=db.insert("notifications",null,cv);
        if(res==-1){
            return "Failed to add Reminder";
        }else{
            return "Reminder added to your calendar";
        }
    }

    public String deleteNotification(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE  FROM " + "notifications" + " WHERE " +
                "notificationId" + " = " + id + ";");
        db.close();
        return "Reminder delete from your calendar";
    }


    public Cursor readNotificationData(int notificationId){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from notifications WHERE notificationId="+notificationId+";";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }

}

