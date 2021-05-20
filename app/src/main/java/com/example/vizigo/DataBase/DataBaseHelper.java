package com.example.vizigo.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.IOException;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Users.db";
    public static final String TABLE_NAME="Users";
    public static final String COL_1="id";
    public static final String COL_2="FullName";
    public static final String COL_3="ContactNumber";
    public static final String COL_4="Email";
    public static final String COL_5="role_id";

    private String[] columns;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(
                    "create table "+ TABLE_NAME +"(id INTEGER PRIMARY KEY, FullName text, ContactNumber text,Email text, role_id INTEGER )"
            );
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS "+TABLE_NAME));
        onCreate(sqLiteDatabase);
    }
    public long addUser(String fullName, String contactNumber,String email, int role_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FullName", fullName);
        contentValues.put("ContactNumber", contactNumber);
        contentValues.put("Email", email);
        contentValues.put("role_id", role_id);
        long res = db.insert(TABLE_NAME,null, contentValues);
        db.close();
        return res;
    }

//    public boolean checkUser(String username, String password) {
//        String[] column = { COL_3 };
//        SQLiteDatabase db = getReadableDatabase();
//        String selection = COL_3 + "=?" + " and " + COL_7 + "=?";
//        String[] selectionArgs = { username, password };
//        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
//        int count = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (count>0)
//            return true;
//        else
//            return false;
//    }
}


