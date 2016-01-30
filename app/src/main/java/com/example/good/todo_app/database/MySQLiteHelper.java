package com.example.good.todo_app.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by aaska on 28/12/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_LIST = "tablelist";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DETAIL = "detail";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ID="id";

    protected static String DB_PATH = "data/data/com.example.good.todo_app/databases/";
    protected static final String DATABASE_NAME = "list.db";
    private SQLiteDatabase db;
    //private final Context myContext;
    public static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LIST + "(" + COLUMN_NAME
            + " text not null, " + COLUMN_DETAIL+ " text not null, " + COLUMN_DATE+ " text not null, " + COLUMN_TIME
            + " text not null, "+COLUMN_ID+" integer);";



    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
/*
        if (db != null && db.isOpen())
            close();

        this.myContext = context;

        try {
            createDataBase();
            openDataBase();
        } catch (IOException e) {
            System.out.println("Exception in creation of database : "
                    + e.getMessage());
            e.printStackTrace();
        }*/
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     *

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            System.out.println("Database Exist");
        } else {
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     *
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            // System.out.println("My Path is:- " + myPath);
            // System.out.println("Open");
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
            // System.out.println("checkDB value:" + checkDB);
            // System.out.println("My Path is:- " + myPath);
        } catch (Exception e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            // System.out.println("Closed");
            checkDB.close();
            // System.out.println("My db is:- " + checkDB.isOpen());
        }

        return checkDB != null ? true : false;
    }

    public void copyCacheToMain(MySQLiteHelper objCache) throws IOException {

        // Open your local db as the input stream
        String inFileName = DB_PATH + DATABASE_NAME;
        InputStream myInput = new FileInputStream(inFileName);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.d("CTM", "Cache To Main Database Copied !");
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     *

    private void copyDataBase() throws IOException {
        // System.out.println("In COpy ");
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        // System.out.println(DB_NAME + "Database Copied !");
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        if (isOpen())
            close();
        db = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public boolean isOpen() {
        if (db != null)
            return db.isOpen();

        return false;
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        // System.out.println("My db is:- " + db.isOpen());
        super.close();
    }

    public synchronized void execNonQuery(String sql) {
        try {

            db.execSQL(sql);
            // Log.d("SQL", sql);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        } finally {
            // closeDb();
        }
    }

    public synchronized Cursor execQuery(String sql) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            // Log.d("SQL", sql);
        } catch (Exception e) {
            Log.e("Err", e.getMessage());
        }
        return cursor;
    }

    public void backup() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "\\data\\com.technotalkative.databaseexaple\\databases\\Student.sqlite";
                // String backupDBPath = "{database name}";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, DATABASE_NAME);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream(backupDB)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {
        }
    }
*/
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST);
        onCreate(db);
    }

}