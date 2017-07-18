package io.github.trisna.myweeklytrack.data;

import android.provider.BaseColumns;

/**
 * Created by trisnawiyatni on 16/07/2017.
 * API Contract for the Weekly Track app
 */

public final class HabitContract {
    //An empty constructor is used for preventing from accidentally instantiating the contract class,
    //by someone
    private HabitContract(){

    }

    public static final class HabitEntry implements BaseColumns{

        /** Name of database table for pets */
        public final static String TABLE_NAME = "habits";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Days.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DAY ="day";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_DATE = "date";

        /**
         * Status of habit.
         *
         * The only possible values are {@link #STATUS_CANCELED}, {@link #STATUS_DONE},
         * or {@link #STATUS_MOVED}.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_STATUS = "status";

        /**
         * Weight of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_HABIT = "habit";

        /**
         * Possible values for the gender of the pet.
         */
        public static final int STATUS_CANCELED = 0;
        public static final int STATUS_DONE = 1;
        public static final int STATUS_MOVED = 2;
    }


}
