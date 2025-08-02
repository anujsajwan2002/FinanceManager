package com.example.financemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.financemanager.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpenseDao {
    private DatabaseHelper dbHelper;

    public ExpenseDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // Insert Expense
    public long insert(Expense expense) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, expense.getTitle());
        values.put(DatabaseHelper.COLUMN_AMOUNT, expense.getAmount());
        values.put(DatabaseHelper.COLUMN_DATE, expense.getDate());
        values.put(DatabaseHelper.COLUMN_CATEGORY, expense.getCategory());

        long result = db.insert(DatabaseHelper.TABLE_EXPENSES, null, values);

        if (result == -1) {
            Log.e("EXP_DEBUG", "❌ Failed to insert expense: " + expense.getTitle());
        } else {
            Log.d("EXP_DEBUG", "✅ Inserted expense with ID: " + result +
                    " | " + expense.getTitle() + " | " + expense.getAmount());
        }

        db.close();
        return result;
    }

    // Get All Expenses
    public List<Expense> getAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_EXPENSES,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_DATE + " DESC"
        );

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                expense.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                expense.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT)));
                expense.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)));
                expense.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY)));

                Log.d("EXP_DEBUG", "📌 Loaded Expense -> " + expense.getId() +
                        " | " + expense.getTitle() + " | " + expense.getAmount());

                expenses.add(expense);
            } while (cursor.moveToNext());
        } else {
            Log.w("EXP_DEBUG", "⚠️ No expenses found in database");
        }

        cursor.close();
        db.close();
        return expenses;
    }

    // Delete Expense
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.TABLE_EXPENSES,
                DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});

        if (rows > 0) {
            Log.d("EXP_DEBUG", "🗑️ Deleted Expense with ID: " + id);
        } else {
            Log.w("EXP_DEBUG", "⚠️ No expense found with ID: " + id);
        }

        db.close();
    }
}
