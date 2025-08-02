package com.example.financemanager.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.financemanager.R;
import com.example.financemanager.database.ExpenseDao;
import com.example.financemanager.models.Expense;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<Expense> expenseList;
    private ExpenseDao expenseDao;

    public ExpenseAdapter(List<Expense> expenseList, ExpenseDao expenseDao) {
        this.expenseList = expenseList;
        this.expenseDao = expenseDao;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense expense = expenseList.get(position);

        holder.title.setText(expense.getTitle());
        holder.amount.setText("₹" + expense.getAmount());
        holder.date.setText(expense.getDate());

        // Long press to delete with confirmation
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Expense")
                    .setMessage("Are you sure you want to delete " + expense.getTitle() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete from database
                        expenseDao.delete(expense.getId());

                        // Remove from list and refresh RecyclerView
                        expenseList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, expenseList.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    static class ExpenseViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, date;

        public ExpenseViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvExpenseTitle);
            amount = itemView.findViewById(R.id.tvExpenseAmount);
            date = itemView.findViewById(R.id.tvExpenseDate);
        }
    }
}
