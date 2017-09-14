package com.example.interntestproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 13.09.2017.
 */

public class UsersOperationBD {
    private UserDBHandler mDbHandler;
    private SQLiteDatabase mDatabase;
    private static final String[] allColumns = {
            UserDBHandler.COLUMN_ID,
            UserDBHandler.COLUMN_FIRST_NAME,
            UserDBHandler.COLUMN_LAST_NAME,
            UserDBHandler.COLUMN_BIRTH_DATE
    };

    public UsersOperationBD(Context context) {
        mDbHandler = new UserDBHandler(context);
    }

    public void open() {
        mDatabase = mDbHandler.getWritableDatabase();
    }

    public void close() {
        mDbHandler.close();
    }

    public ObjectUserDataBase addUser(ObjectUserDataBase objectUserDataBase) {
        ContentValues values = new ContentValues();
        values.put(UserDBHandler.COLUMN_FIRST_NAME, objectUserDataBase.getFirstName());
        values.put(UserDBHandler.COLUMN_LAST_NAME, objectUserDataBase.getLastName());
        values.put(UserDBHandler.COLUMN_BIRTH_DATE, objectUserDataBase.getDate());
        long insertId = mDatabase.insert(UserDBHandler.TABLE_USERS, null, values);
        objectUserDataBase.setUsId(insertId);
        return objectUserDataBase;
    }
    public ObjectUserDataBase getUser(long id)
    {
        Cursor cursor = mDatabase.query(UserDBHandler.TABLE_USERS,allColumns,UserDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ObjectUserDataBase o = new ObjectUserDataBase(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return o;
    }

    public List<ObjectUserDataBase> getAllUsers()
    {
        Cursor cursor = mDatabase.query(UserDBHandler.TABLE_USERS,allColumns,null,null,null, null, null);
        List <ObjectUserDataBase> allUsers= new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                ObjectUserDataBase ob = new ObjectUserDataBase();
                ob.setUsId(cursor.getLong(cursor.getColumnIndex(UserDBHandler.COLUMN_ID)));
                ob.setFirstName(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_FIRST_NAME)));
                ob.setLastName(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_LAST_NAME)));
                ob.setDate(cursor.getString(cursor.getColumnIndex(UserDBHandler.COLUMN_BIRTH_DATE)));
                allUsers.add(ob);
            }
        }
        return allUsers;
    }
    public int updateUsers(ObjectUserDataBase ob) {

        ContentValues values = new ContentValues();
        values.put(UserDBHandler.COLUMN_FIRST_NAME, ob.getFirstName());
        values.put(UserDBHandler.COLUMN_LAST_NAME, ob.getLastName());
        values.put(UserDBHandler.COLUMN_BIRTH_DATE, ob.getDate());
        return mDatabase.update(UserDBHandler.TABLE_USERS, values,
                UserDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(ob.getUsId())});
    }
    public void removeUser(ObjectUserDataBase objectUserDataBase) {

        mDatabase.delete(UserDBHandler.TABLE_USERS, UserDBHandler.COLUMN_ID + "=" + objectUserDataBase.getUsId(), null);
    }
 public boolean isExist()
 {
     return mDbHandler.isExist();
 }
 public  boolean isOpen(){return mDatabase.isOpen();}
}
