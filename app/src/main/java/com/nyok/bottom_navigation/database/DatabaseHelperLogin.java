package com.nyok.bottom_navigation.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelperLogin extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DB_LOGIN";

    public DatabaseHelperLogin(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tambahkan kolom email pada tabel users
        db.execSQL("CREATE TABLE session (id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE users (id integer PRIMARY KEY AUTOINCREMENT, email text, username text, password text)"); // Email ditambahkan
        db.execSQL("INSERT INTO session(id, login) VALUES (1, 'kosong')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    // Check session
    public Boolean checkSession(String value) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM session WHERE login = ?", new String[]{value});
        return cursor.getCount() > 0;
    }

    // Upgrade session
    public Boolean upgradeSession(String value, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("login", value);
        long update = db.update("session", values, "id = ?", new String[]{String.valueOf(id)});
        return update != -1;
    }

    // Input user dengan email
    public boolean simpanUser(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);  // Simpan email juga
        values.put("username", username);
        values.put("password", password);
        long insert = db.insert("users", null, values);
        return insert != -1;
    }

    // Check login
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }
}
