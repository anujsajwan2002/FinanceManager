package com.example.financemanager.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.R;
import com.example.financemanager.database.BillDao;
import com.example.financemanager.models.Bill;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

public class AddBillActivity extends AppCompatActivity {

    EditText etTitle, etAmount, etDueDate, etCategory;
    Button btnSaveBill;
    BillDao billDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        Log.d("NAV_TEST", "Opened AddBillActivity");

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etDueDate = findViewById(R.id.etDueDate);
        etCategory = findViewById(R.id.etCategory);
        btnSaveBill = findViewById(R.id.btnSaveBill);

        billDao = new BillDao(this);

        // 📌 Show DatePicker when Due Date is clicked
        etDueDate.setOnClickListener(v -> showDatePicker());

        btnSaveBill.setOnClickListener(v -> saveBill());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is 0-indexed → add 1
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    etDueDate.setText(date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void saveBill() {
        if (etTitle.getText().toString().isEmpty() ||
                etAmount.getText().toString().isEmpty() ||
                etDueDate.getText().toString().isEmpty() ||
                etCategory.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Bill bill = new Bill();
        bill.setTitle(etTitle.getText().toString());
        try {
            bill.setAmount(Double.parseDouble(etAmount.getText().toString()));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        bill.setDueDate(etDueDate.getText().toString());
        bill.setCategory(etCategory.getText().toString());

        billDao.insert(bill);
        scheduleBillReminder(bill);
        Toast.makeText(this, "Bill Added", Toast.LENGTH_SHORT).show();
        finish(); // Return to MainActivity
    }


    private void scheduleBillReminder(Bill bill) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // For testing: schedule 1 minute from now
        long triggerTime = System.currentTimeMillis() + 60 * 1000;

        Intent intent = new Intent(this, com.example.financemanager.receivers.BillReminderReceiver.class);
        intent.putExtra("billTitle", bill.getTitle());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                bill.getId(), // unique request code
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
    }

}
