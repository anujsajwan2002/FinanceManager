package com.example.financemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financemanager.models.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDao {
    private DatabaseHelper dbHelper;

    public BillDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void insert(Bill bill) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, bill.getTitle());
        values.put(DatabaseHelper.COLUMN_AMOUNT, bill.getAmount());
        values.put(DatabaseHelper.COLUMN_DUE_DATE, bill.getDueDate());
        values.put(DatabaseHelper.COLUMN_CATEGORY, bill.getCategory());
        db.insert(DatabaseHelper.TABLE_BILLS, null, values);
        db.close();
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BILLS, null,
                null, null, null, null,
                DatabaseHelper.COLUMN_DUE_DATE + " ASC");

        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                bill.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TITLE)));
                bill.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AMOUNT)));
                bill.setDueDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DUE_DATE)));
                bill.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CATEGORY)));
                bills.add(bill);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bills;
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_BILLS, DatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}

