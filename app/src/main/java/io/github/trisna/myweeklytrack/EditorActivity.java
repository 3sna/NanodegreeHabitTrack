package io.github.trisna.myweeklytrack;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import io.github.trisna.myweeklytrack.data.HabitContract.HabitEntry;
import io.github.trisna.myweeklytrack.data.HabitDbHelper;




/**
 * Created by trisnawiyatni on 16/07/2017.
 *
 * Allows user to create a new habit or edit an existing one
 */

public class EditorActivity extends AppCompatActivity{
    /** EditText field to enter the pet's name */
    private EditText mDayEditText;

    /** EditText field to enter the pet's breed */
    private EditText mDateEditText;

    /** EditText field to enter the pet's weight */
    private EditText mHabitEditText;

    /** EditText field to enter the pet's gender */
    private Spinner mStatusSpinner;

    /**
     * Gender of the pet. The possible valid values are in the PetContract.java file:
     * {@link HabitEntry#STATUS_CANCELED}, {@link HabitEntry#STATUS_DONE}, or
     * {@link HabitEntry#STATUS_MOVED}.
     */
    private int mStatus = HabitEntry.STATUS_CANCELED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mDayEditText = (EditText) findViewById(R.id.edit_day);
        mDateEditText = (EditText) findViewById(R.id.edit_date);
        mHabitEditText = (EditText) findViewById(R.id.edit_habit);
        mStatusSpinner = (Spinner) findViewById(R.id.spinner_status);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_status_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mStatusSpinner.setAdapter(statusSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.status_done))) {
                        mStatus = HabitEntry.STATUS_DONE;
                    } else if (selection.equals(getString(R.string.status_moved))) {
                        mStatus = HabitEntry.STATUS_MOVED;
                    } else {
                        mStatus = HabitEntry.STATUS_CANCELED;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mStatus = HabitEntry.STATUS_CANCELED;
            }
        });
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void insertHabit() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String dayString = mDayEditText.getText().toString().trim();
        String dateString = mDateEditText.getText().toString().trim();
        String habitString = mHabitEditText.getText().toString().trim();
        //int habit = Integer.parseInt(habitString);

        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and habits attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_DAY, dayString);
        values.put(HabitEntry.COLUMN_DATE, dateString);
        values.put(HabitEntry.COLUMN_STATUS, mStatus);
        values.put(HabitEntry.COLUMN_HABIT, habitString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habbit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                insertHabit();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
