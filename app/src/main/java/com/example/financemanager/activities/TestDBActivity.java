package com.example.financemanager.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.database.BillDao;
import com.example.financemanager.database.ExpenseDao;
import com.example.financemanager.models.Bill;
import com.example.financemanager.models.Expense;

import java.util.List;

public class TestDBActivity extends AppCompatActivity {
    private static final String TAG = "DB_TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpenseDao expenseDao = new ExpenseDao(this);
        BillDao billDao = new BillDao(this);

        // ---------- TEST EXPENSES ----------
        Log.d(TAG, "==== Testing Expenses ====");

        Expense e1 = new Expense();
        e1.setTitle("Lunch");
        e1.setAmount(250.50);
        e1.setDate("2025-08-02");
        e1.setCategory("Food");

        expenseDao.insert(e1);
        Log.d(TAG, "Inserted Expense: " + e1.getTitle());

        List<Expense> expenses = expenseDao.getAllExpenses();
        for (Expense exp : expenses) {
            Log.d(TAG, "Expense -> ID: " + exp.getId() +
                    " | " + exp.getTitle() + " | ₹" + exp.getAmount());
        }

        if (!expenses.isEmpty()) {
            expenseDao.delete(expenses.get(0).getId());
            Log.d(TAG, "Deleted Expense with ID: " + expenses.get(0).getId());
        }

        // ---------- TEST BILLS ----------
        Log.d(TAG, "==== Testing Bills ====");

        Bill b1 = new Bill();
        b1.setTitle("Electricity Bill");
        b1.setAmount(1200.00);
        b1.setDueDate("2025-08-10");
        b1.setCategory("Utilities");

        billDao.insert(b1);
        Log.d(TAG, "Inserted Bill: " + b1.getTitle());

        List<Bill> bills = billDao.getAllBills();
        for (Bill bill : bills) {
            Log.d(TAG, "Bill -> ID: " + bill.getId() +
                    " | " + bill.getTitle() + " | Due: " + bill.getDueDate());
        }

        if (!bills.isEmpty()) {
            billDao.delete(bills.get(0).getId());
            Log.d(TAG, "Deleted Bill with ID: " + bills.get(0).getId());
        }
    }
}
