package com.example.financemanager.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.financemanager.R;
import com.example.financemanager.adapters.BillAdapter;
import com.example.financemanager.database.BillDao;
import com.example.financemanager.models.Bill;

import java.util.List;

public class BillsListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BillAdapter billAdapter;
    BillDao billDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_list);
        Log.d("NAV_TEST", "✅ Opened BillsListActivity");

        recyclerView = findViewById(R.id.recyclerViewBills);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        billDao = new BillDao(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("BILL_DEBUG", "🔄 Refreshing bill list...");

        // Fetch latest bills every time activity comes to front
        List<Bill> bills = billDao.getAllBills();
        billAdapter = new BillAdapter(bills, billDao);
        recyclerView.setAdapter(billAdapter);
    }
}
