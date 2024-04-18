package com.example.preservenaturetogether.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.preservenaturetogether.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 2;
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        copyDataBase();
        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File file = new File(DB_PATH + DB_NAME);
            if (file.exists()) file.delete();
            copyDataBase();
            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File file = new File(DB_PATH + DB_NAME);
        return file.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException e) {
                throw new Error("ErrorCopyDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream inputStream = mContext.getResources().openRawResource(R.raw.database);
        OutputStream outputStream = new FileOutputStream(DB_PATH + DB_NAME);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public boolean openDataBase() throws IOException {
        mDatabase = SQLiteDatabase.openDatabase(
                DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY
        );
        return mDatabase != null;
    }

    @Override
    public synchronized void close() {
        if (mDatabase != null) mDatabase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) mNeedUpdate = true;
    }
}