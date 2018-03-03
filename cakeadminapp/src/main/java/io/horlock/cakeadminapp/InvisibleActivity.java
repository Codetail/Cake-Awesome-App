package io.horlock.cakeadminapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 00003130 on 12/10/17.
 */

public class InvisibleActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    sendNotification();
    finish();
  }

  private void sendNotification() {
    NotificationCompat.Builder builder =
        new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle("New notification")
            .setContentText("You received a new order");
    PendingIntent contentIntent = PendingIntent.getActivity(this,
        1, new Intent(this, MainActivity.class),
        PendingIntent.FLAG_UPDATE_CURRENT);
    builder.setContentIntent(contentIntent);
    NotificationManager notificationManager = (NotificationManager) getSystemService(
        Context.NOTIFICATION_SERVICE);
    notificationManager.notify(1, builder.build());
  }
}

