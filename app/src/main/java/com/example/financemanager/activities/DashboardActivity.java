//package com.example.financemanager.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.financemanager.R;
//
//public class DashboardActivity extends AppCompatActivity {
//    Button btnAddBill, btnViewBills, btnAddExpense, btnViewExpenses;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//
//        btnAddBill = findViewById(R.id.btnAddBill);
//        btnViewBills = findViewById(R.id.btnViewBills);
//        btnAddExpense = findViewById(R.id.btnAddExpense);
//        btnViewExpenses = findViewById(R.id.btnViewExpenses);
//
//        btnAddBill.setOnClickListener(v ->
//                startActivity(new Intent(this, AddBillActivity.class)));
//
//        btnViewBills.setOnClickListener(v ->
//                startActivity(new Intent(this, BillsListActivity.class)));
//
//        btnAddExpense.setOnClickListener(v ->
//                startActivity(new Intent(this, AddExpenseActivity.class)));
//
//        btnViewExpenses.setOnClickListener(v ->
//                startActivity(new Intent(this, ExpensesListActivity.class)));
//    }
//}
//
