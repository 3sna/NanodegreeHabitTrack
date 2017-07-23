package io.github.trisna.myweeklytrack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.github.trisna.myweeklytrack.data.HabitContract.HabitEntry;
import io.github.trisna.myweeklytrack.data.HabitDbHelper;


/*
* Display list of habits in a week and stored in the app
* */

public class WeeklyActivity extends AppCompatActivity {

    /** Database helper that will provide us access to database */
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WeeklyActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo(){
        //Create and, or open database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Define a projection that specifies which columns from database
        //you will actually use after this query

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_DAY,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_STATUS,
                HabitEntry.COLUMN_HABIT };

        // Perform a query on the habits table

    Cursor cursor = db.query(
            HabitEntry.TABLE_NAME,   // The table to query
            projection,            // The columns to return
            null,                  // The columns for the WHERE clause
            null,                  // The values for the WHERE clause
            null,                  // Don't group the rows
            null,                  // Don't filter by row groups
            null);                   // The sort order

    TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
        // Create a header in the Text View that looks like this:
        //
        // The pets table contains <number of rows in Cursor> pets.
        // _id - name - breed - gender - weight
        //
        // In the while loop below, iterate through the rows of the cursor and display
        // the information from each column in this order.
        displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
        displayView.append(HabitEntry._ID + " - " +
                HabitEntry.COLUMN_DAY + " - " +
                HabitEntry.COLUMN_DATE + " - " +
                HabitEntry.COLUMN_STATUS + " - " +
                HabitEntry.COLUMN_HABIT + "\n");

        // Figure out the index of each column
        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int dayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DAY);
        int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
        int statusColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_STATUS);
        int habitColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT);

        // Iterate through all the returned rows in the cursor
        while (cursor.moveToNext()) {
            // Use that index to extract the String or Int value of the word
            // at the current row the cursor is on.
            int currentID = cursor.getInt(idColumnIndex);
            String currentDay = cursor.getString(dayColumnIndex);
            String currentDate = cursor.getString(dateColumnIndex);
            int currentStatus = cursor.getInt(statusColumnIndex);
            String currentHabit = cursor.getString(habitColumnIndex);
            // Display the values from each column of the current row in the cursor in the TextView
            displayView.append(("\n" + currentID + " - " +
                    currentDay + " - " +
                    currentDate + " - " +
                    currentStatus + " - " +
                    currentHabit));
        }
    } finally {
        // Always close the cursor when you're done reading from it. This releases all its
        // resources and makes it invalid.
        cursor.close();
    }
}

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertHabit() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and Toto's pet attributes are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_DAY, "Monday");
        values.put(HabitEntry.COLUMN_DATE, "17 July 2017");
        values.put(HabitEntry.COLUMN_STATUS, HabitEntry.STATUS_DONE);
        values.put(HabitEntry.COLUMN_HABIT, "Course nanodegree");

        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_weekly, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
   }
}
