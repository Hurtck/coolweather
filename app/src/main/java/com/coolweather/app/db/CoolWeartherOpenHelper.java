package com.coolweather.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.name;

/**
 * Created by 47321 on 2016/9/27 0027.
 */

public class CoolWeartherOpenHelper extends SQLiteOpenHelper {

    public CoolWeartherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
    }
    /**
     * Province表 SQL建表语句
     */
    public static  final  String CREATE_PROVICE = "create table Province("
            +"id integer primary key autoincrement,"
            +"province_name text,"
            +"province_code text)";

    /**
     * City表 SQL建表语句
     * */
    public static final String  CREATE_CITY = "create table City("
            +"id integer primary key autoincrement,"
            +"city_name text,"
            +"city_code text,"
            +"province_id integer)";

    /***
     * County表 SQL建表语句
     */
    public  static  final  String CREATE_COUNTY = "create table County("
            +"id integer primary key autoincrement,"
            +"county_name text,"
            +"county_code text,"
            +"city_id integer)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVICE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
