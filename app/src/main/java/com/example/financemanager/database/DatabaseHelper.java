package com.example.financemanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "finance_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_BILLS = "bills";
    public static final String TABLE_EXPENSES = "expenses";

    // Common columns
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_CATEGORY = "category";

    // Bills table specific
    public static final String COLUMN_DUE_DATE = "dueDate";

    // Expenses table specific
    public static final String COLUMN_DATE = "date";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Bills table
        String CREATE_BILLS_TABLE = "CREATE TABLE " + TABLE_BILLS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AMOUNT + " REAL, "
                + COLUMN_DUE_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT)";
        db.execSQL(CREATE_BILLS_TABLE);

        // Create Expenses table
        String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_AMOUNT + " REAL, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT)";
        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }
}
