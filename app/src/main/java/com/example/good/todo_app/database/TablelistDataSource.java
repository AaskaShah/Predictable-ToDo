package com.example.good.todo_app.database;

/**
 * Created by aaska on 29/12/15.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.good.todo_app.Listitem;

public class TablelistDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_DETAIL ,MySQLiteHelper.COLUMN_DATE ,MySQLiteHelper.COLUMN_ALARMINFO,MySQLiteHelper.COLUMN_ID};
    Context context;

    public TablelistDataSource(Context c) {
        dbHelper = new MySQLiteHelper(c);
        context=c;

    }
/*
    public TablelistDataSource opnToRead() {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getReadableDatabase();
        return this;

    }

    public TablelistDataSource opnToWrite() {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;

    }
*/
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertlist(String name,String details,String date,String info,int id) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_DETAIL, details);
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        values.put(MySQLiteHelper.COLUMN_ALARMINFO, info);
        values.put(MySQLiteHelper.COLUMN_ID, id);
        long insertId = database.insert(MySQLiteHelper.TABLE_LIST, null,
                values);
        /*Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();                                                    to return newList that is the new item added.
        Listitem newList = cursorToComment(cursor);
        cursor.close();*/
        return insertId;
    }

    public void deletelist(Listitem item) {
        long id = item.getId();
        System.out.println("List deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_LIST, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void alterlist(Listitem item,String alminfo){
        long id = item.getId();
        database.execSQL("UPDATE " + MySQLiteHelper.TABLE_LIST + " SET " + MySQLiteHelper.COLUMN_ALARMINFO + " = '" + alminfo + "' WHERE " + MySQLiteHelper.COLUMN_ID + " = " + id);

    }

    public List<Listitem> getAllItems() {
        List<Listitem> list = new ArrayList<Listitem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LIST,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Listitem item = cursorToItem(cursor);
            list.add(item);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return list;
    }

    public Listitem getitemwithid(int id){
        Listitem l=new Listitem(null,null,null,null,0);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_LIST,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Listitem item = cursorToItem(cursor);
            if (item.getId()==id)
                l=item;
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return l;
    }

    private Listitem cursorToItem(Cursor cursor) {
        Listitem item = new Listitem(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4));

        return item;
    }
}


