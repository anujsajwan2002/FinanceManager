package com.example.financemanager.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.financemanager.MainActivity;
import com.example.financemanager.R;
import com.example.financemanager.utils.NotificationUtils;

public class BillReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String billTitle = intent.getStringExtra("billTitle");

        // Intent to open the app when notification is tapped
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground) // use app icon
                .setContentTitle("Bill Reminder")
                .setContentText("Your bill is due: " + billTitle)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
