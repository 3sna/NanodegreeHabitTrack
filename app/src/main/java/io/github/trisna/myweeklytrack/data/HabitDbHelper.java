package io.github.trisna.myweeklytrack.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.github.trisna.myweeklytrack.data.HabitContract.HabitEntry;

/**
 * Created by trisnawiyatni on 16/07/2017.
 * Database helper for the Weekly Track app.
 * Manages database creation and version management.
 */

public class HabitDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "habits.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link HabitDbHelper}.
     *
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_HABITS_TABLE =  "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_DAY + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_DATE  + " TEXT, "
                + HabitEntry.COLUMN_STATUS + " INTEGER NOT NULL, "
                + HabitEntry.COLUMN_HABIT + " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
