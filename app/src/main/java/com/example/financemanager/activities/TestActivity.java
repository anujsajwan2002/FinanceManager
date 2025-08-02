package com.example.financemanager.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.database.BillDao;
import com.example.financemanager.database.ExpenseDao;
import com.example.financemanager.models.Bill;
import com.example.financemanager.models.Expense;
import com.example.financemanager.R;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    private BillDao billDao;
    private ExpenseDao expenseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test); // blank layout

        billDao = new BillDao(this);
        expenseDao = new ExpenseDao(this);

        Log.d("DB_TEST", "==== Testing Expenses ====");
        testExpenses();

        Log.d("DB_TEST", "==== Testing Bills ====");
        testBills();
    }

    private void testExpenses() {
        // Clear previous data
        expenseDao.delete(1); // example: delete ID=1 if exists

        // Insert
        Expense e = new Expense();
        e.setTitle("Lunch");
        e.setAmount(250.5);
        e.setDate("2025-08-02");
        e.setCategory("Food");
        expenseDao.insert(e);

        Log.d("DB_TEST", "Inserted Expense: " + e.getTitle());

        // Fetch
        List<Expense> expenses = expenseDao.getAllExpenses();
        for (Expense ex : expenses) {
            Log.d("DB_TEST", "Expense -> ID: " + ex.getId() + " | " + ex.getTitle() +
                    " | ₹" + ex.getAmount() + " | Date: " + ex.getDate());
        }

        // Delete the last inserted expense
        if (!expenses.isEmpty()) {
            int deleteId = expenses.get(expenses.size() - 1).getId();
            expenseDao.delete(deleteId);
            Log.d("DB_TEST", "Deleted Expense with ID: " + deleteId);
        }
    }

    private void testBills() {
        // Insert
        Bill b = new Bill();
        b.setTitle("Electricity Bill");
        b.setAmount(500.0);
        b.setDueDate("2025-08-10");
        b.setCategory("Utilities");
        billDao.insert(b);

        Log.d("DB_TEST", "Inserted Bill: " + b.getTitle());

        // Fetch
        List<Bill> bills = billDao.getAllBills();
        for (Bill bill : bills) {
            Log.d("DB_TEST", "Bill -> ID: " + bill.getId() + " | " + bill.getTitle() +
                    " | ₹" + bill.getAmount() + " | Due: " + bill.getDueDate());
        }

        if (!bills.isEmpty()) {
            int deleteId = bills.get(bills.size() - 1).getId();
            billDao.delete(deleteId);
            Log.d("DB_TEST", "Deleted Bill with ID: " + deleteId);
        }

    }
}

