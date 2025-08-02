package com.example.financemanager.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.financemanager.R;
import com.example.financemanager.database.BillDao;
import com.example.financemanager.models.Bill;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {

    private List<Bill> billList;
    private BillDao billDao;

    public BillAdapter(List<Bill> billList, BillDao billDao) {
        this.billList = billList;
        this.billDao = billDao;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bill, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Bill bill = billList.get(position);

        holder.title.setText(bill.getTitle());
        holder.amount.setText("₹" + bill.getAmount());
        holder.dueDate.setText("Due: " + bill.getDueDate());

        // Long press to delete
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete Bill")
                    .setMessage("Are you sure you want to delete " + bill.getTitle() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Delete from DB
                        billDao.delete(bill.getId());

                        // Remove from list and update RecyclerView
                        billList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, billList.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView title, amount, dueDate;

        public BillViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvBillTitle);
            amount = itemView.findViewById(R.id.tvBillAmount);
            dueDate = itemView.findViewById(R.id.tvBillDueDate);
        }
    }
}
