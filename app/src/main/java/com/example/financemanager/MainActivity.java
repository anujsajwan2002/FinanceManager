package com.example.financemanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.activities.AddBillActivity;
import com.example.financemanager.activities.AddExpenseActivity;
import com.example.financemanager.activities.BillsListActivity;
import com.example.financemanager.activities.ExpensesListActivity;
import com.example.financemanager.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity {

    Button btnAddBill, btnViewBills , btnAddExpense , btnViewExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationUtils.createNotificationChannel(this);


        // Initialize buttons
        btnAddBill = findViewById(R.id.btnAddBill);
        btnViewBills = findViewById(R.id.btnViewBills);
        btnAddExpense = findViewById(R.id.btnAddExpense);
        btnViewExpenses = findViewById(R.id.btnViewExpenses);

        // Navigate to Add Bill
        btnAddBill.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddBillActivity.class))
        );

        // Navigate to Bills List
        btnViewBills.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, BillsListActivity.class))
        );

        // Navigate to Add Expense
        btnAddExpense.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class))
        );

        // Navigate to Expenses List
        btnViewExpenses.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ExpensesListActivity.class))
        );
    }
}