package com.example.androidposlinkdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.SparseArray;

import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationCompat;

/**
 * Created by Kim.L 8/18/21
 */
public class ServiceNotificationSet {

    private static final ServiceNotificationSet instance = new ServiceNotificationSet();

    private final SparseArray<String> textLines = new SparseArray<>();

    private ServiceNotificationSet() {

    }

    public static ServiceNotificationSet getInstance() {
        return instance;
    }

    public ServiceNotificationSet addContentText(int line, String content) {
        synchronized (textLines) {
            textLines.append(line, content);
        }
        return this;
    }

    public ServiceNotificationSet removeContentText(int line) {
        synchronized (textLines) {
            textLines.remove(line);
        }
        return this;
    }

    public Notification buildNotification(Context context, String channelId,
                                          @DrawableRes int iconId) {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            //Fix invalid channel for service notification: Notification exception
            CharSequence name = "BroadPOS Service";
            String description = "BroadPOS Component service";
            int importance = NotificationManager.IMPORTANCE_MIN; //no sound, no vabration
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.enableVibration(false);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, channelId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        }
        builder.setSmallIcon(iconId)
                .setContentText("Components are running");

        int size = textLines.size();
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        synchronized (textLines) {
            for (int i = 0; i < size; i++) {
                int key = textLines.keyAt(i);
                inboxStyle.addLine(textLines.get(key));
            }
        }
        builder.setStyle(inboxStyle);
        return builder.build();
    }

    public void clear() {
        synchronized (textLines) {
            textLines.clear();
        }
    }
}
