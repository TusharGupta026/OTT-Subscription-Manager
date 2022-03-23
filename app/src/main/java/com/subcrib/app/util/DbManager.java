package com.subcrib.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {

    private static final String dbname="dbsubscriptions";


    public DbManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table subscriptions (id integer primary key autoincrement ,amount text,subscription text,description text,payment text,email text,billingDate text,billingPeriod text)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query="DROP TABLE IF EXISTS subscriptions";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public String addRecord(String amount, String subscription, String description, String payment, String email, String billingDate, String billingPeriod){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("amount",amount);
        cv.put("subscription",subscription);
        cv.put("description",description);
        cv.put("payment",payment);
        cv.put("email",email);
        cv.put("billingDate",billingDate);
        cv.put("billingPeriod",billingPeriod);

        float res=db.insert("subscriptions",null,cv);
        if(res==-1){
            return "Failed";
        }else{
            return "Successfully Added";
        }
    }


    public Cursor readalldata(){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="select * from subscriptions order by id ";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }

    public String deleteItem(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE  FROM " + "subscriptions" + " WHERE " +
                "id" + " = " + id + ";");
        db.close();
        return "Deleted Successfully";
    }



}

