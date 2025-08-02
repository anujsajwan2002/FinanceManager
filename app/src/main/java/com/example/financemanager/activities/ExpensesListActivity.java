package com.example.financemanager.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financemanager.R;
import com.example.financemanager.adapters.ExpenseAdapter;
import com.example.financemanager.database.ExpenseDao;
import com.example.financemanager.models.Expense;

import java.util.List;

public class ExpensesListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;
    ExpenseDao expenseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
        Log.d("NAV_TEST", "✅ Opened ExpensesListActivity");

        recyclerView = findViewById(R.id.recyclerViewExpenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        expenseDao = new ExpenseDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("EXP_DEBUG", "🔄 Refreshing expense list...");

        // Fetch latest expenses every time activity comes to front
        List<Expense> expenses = expenseDao.getAllExpenses();
        expenseAdapter = new ExpenseAdapter(expenses, expenseDao);
        recyclerView.setAdapter(expenseAdapter);
    }
}
