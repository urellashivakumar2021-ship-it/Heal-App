package com.shiva.healapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "heal.db";
    private static final int DB_VER = 3;  // DATABASE VERSION UPDATED

    // USER TABLE
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USER_NAME = "name";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_PASSWORD = "password";

    // HEALTH LOG TABLE
    public static final String TABLE_HEALTH = "health_logs";
    public static final String COL_HEALTH_ID = "id";
    public static final String COL_HEALTH_USER_ID = "user_id";
    public static final String COL_SLEEP = "sleep";
    public static final String COL_WATER = "water";
    public static final String COL_STRESS = "stress";
    public static final String COL_DIET = "diet";
    public static final String COL_ACTIVITY = "activity";
    public static final String COL_MOOD = "mood";
    public static final String COL_HEART = "heartrate";
    public static final String COL_TIMESTAMP = "timestamp";

    // DOCTOR TABLE
    public static final String TABLE_DOCTORS = "doctors";
    public static final String COL_DOC_ID = "id";
    public static final String COL_DOC_NAME = "name";
    public static final String COL_DOC_EMAIL = "email";
    public static final String COL_DOC_PASSWORD = "password";
    public static final String COL_DOC_SPECIALIZATION = "specialization";
    public static final String COL_DOC_PHONE = "phone";

    // ADVICE TABLE
    public static final String TABLE_ADVICE = "advice";
    public static final String COL_ADVICE_ID = "id";
    public static final String COL_ADVICE_USER_ID = "user_id";
    public static final String COL_ADVICE_DOCTOR_ID = "doctor_id";
    public static final String COL_ADVICE_TEXT = "advice_text";
    public static final String COL_ADVICE_TIMESTAMP = "timestamp";

    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // USERS TABLE
        db.execSQL("CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)");

        // HEALTH LOGS TABLE
        db.execSQL("CREATE TABLE health_logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "sleep INTEGER, " +
                "water REAL, " +
                "stress INTEGER, " +
                "diet TEXT, " +
                "activity TEXT, " +
                "mood TEXT, " +
                "heartrate INTEGER, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");

        // DOCTORS TABLE
        db.execSQL("CREATE TABLE doctors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT, " +
                "specialization TEXT, " +
                "phone TEXT)");

        // ADVICE TABLE
        db.execSQL("CREATE TABLE advice (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "doctor_id INTEGER, " +
                "advice_text TEXT, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS health_logs");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS doctors");
        db.execSQL("DROP TABLE IF EXISTS advice");
        onCreate(db);
    }

    // USER REGISTER
    public long registerUser(String name, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        return getWritableDatabase().insert("users", null, cv);
    }

    // USER LOGIN
    public Cursor findUserByEmailPassword(String email, String password) {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM users WHERE email=? AND password=?",
                new String[]{email, password});
    }

    // INSERT HEALTH LOG
    public long insertHealthLog(int userId, int sleep, double water, int stress,
                                String diet, String activity, String mood, int heart) {

        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("sleep", sleep);
        cv.put("water", water);
        cv.put("stress", stress);
        cv.put("diet", diet);
        cv.put("activity", activity);
        cv.put("mood", mood);
        cv.put("heartrate", heart);

        return getWritableDatabase().insert("health_logs", null, cv);
    }

    // DOCTOR REGISTER
    public long registerDoctor(String name, String email, String password,
                               String specialization, String phone) {

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("password", password);
        cv.put("specialization", specialization);
        cv.put("phone", phone);

        return getWritableDatabase().insert("doctors", null, cv);
    }

    // DOCTOR LOGIN
    public Cursor findDoctorByEmailPassword(String email, String password) {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM doctors WHERE email=? AND password=?",
                new String[]{email, password});
    }

    // GET ALL DOCTORS
    public Cursor getAllDoctors() {
        return getReadableDatabase().rawQuery("SELECT * FROM doctors", null);
    }

    // DELETE DOCTOR
    public int deleteDoctor(int doctorId) {
        return getWritableDatabase()
                .delete("doctors", "id=?", new String[]{String.valueOf(doctorId)});
    }

    // UPDATE USER PROFILE (Name + Password)
    public int updateUser(int id, String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("password", password);
        return db.update("users", cv, "id=?", new String[]{String.valueOf(id)});
    }


    // GET ALL USERS
    public Cursor getAllUsers() {
        return getReadableDatabase().rawQuery("SELECT * FROM users", null);
    }

    // DELETE USER
    public int deleteUser(int userId) {
        return getWritableDatabase()
                .delete("users", "id=?", new String[]{String.valueOf(userId)});
    }

    // GET USER LOGS — ORDER BY LATEST FIRST
    public Cursor getUserLogs(int userId) {
        return getReadableDatabase().rawQuery(
                "SELECT * FROM health_logs WHERE user_id=? ORDER BY timestamp DESC",
                new String[]{String.valueOf(userId)});
    }

    // INSERT DOCTOR ADVICE
    public long insertAdvice(int userId, int doctorId, String adviceText) {
        ContentValues cv = new ContentValues();
        cv.put("user_id", userId);
        cv.put("doctor_id", doctorId);
        cv.put("advice_text", adviceText);
        return getWritableDatabase().insert("advice", null, cv);
    }
    // GET ADVICE FOR USER
    public Cursor getAdviceForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM advice WHERE user_id=? ORDER BY timestamp DESC",
                new String[]{String.valueOf(userId)}
        );
    }


    // GET ADVICE FOR USER (JOIN DOCTOR DETAILS)
    public Cursor getAdviceWithDoctor(int userId) {
        return getReadableDatabase().rawQuery(
                "SELECT advice.advice_text, advice.timestamp, " +
                        "doctors.name AS doctor_name, doctors.specialization AS doctor_spec " +
                        "FROM advice " +
                        "JOIN doctors ON advice.doctor_id = doctors.id " +
                        "WHERE advice.user_id=? " +
                        "ORDER BY advice.timestamp DESC",
                new String[]{String.valueOf(userId)}
        );
    }

    // Get user name from users table
    public String getUserNameById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM users WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            cursor.close();
            return name;
        }
        cursor.close();
        return "";
    }

    // Get doctor name from doctors table
    public String getDoctorNameById(int doctorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM doctors WHERE id = ?", new String[]{String.valueOf(doctorId)});
        if (cursor.moveToFirst()) {
            String name = cursor.getString(0);
            cursor.close();
            return name;
        }
        cursor.close();
        return "";
    }

}
