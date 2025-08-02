package com.example.financemanager.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.financemanager.R;
import com.example.financemanager.database.ExpenseDao;
import com.example.financemanager.models.Expense;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {

    EditText etTitle, etAmount, etDate, etCategory;
    Button btnSaveExpense;
    ExpenseDao expenseDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Log.d("EXP_DEBUG", "✅ AddExpenseActivity opened");

        etTitle = findViewById(R.id.etTitle);
        etAmount = findViewById(R.id.etAmount);
        etDate = findViewById(R.id.etDate);
        etCategory = findViewById(R.id.etCategory);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        expenseDao = new ExpenseDao(this);

        etDate.setOnClickListener(v -> showDatePicker());

        btnSaveExpense.setOnClickListener(v -> {
            Log.d("EXP_DEBUG", "🟢 Save button clicked");
            saveExpense();
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    etDate.setText(date);
                    Log.d("EXP_DEBUG", "📅 Date selected: " + date);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void saveExpense() {
        String title = etTitle.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        if (title.isEmpty() || amountStr.isEmpty() || date.isEmpty() || category.isEmpty()) {
            Log.w("EXP_DEBUG", "⚠️ One or more fields are empty");
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Log.e("EXP_DEBUG", "❌ Invalid amount entered: " + amountStr, e);
            Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setCategory(category);

        Log.d("EXP_DEBUG", "➡️ Inserting Expense: " + title + " | " + amount + " | " + date + " | " + category);
        expenseDao.insert(expense);

        Toast.makeText(this, "Expense Added!", Toast.LENGTH_SHORT).show();
        Log.d("EXP_DEBUG", "✅ Expense saved successfully");

        finish(); // Go back to dashboard
    }
}
