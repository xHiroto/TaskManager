package sg.edu.rp.c346.id19045784.taskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationReceiver extends BroadcastReceiver {

    int reqCode = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new
                    NotificationChannel("default", "Default Channel", NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription("This is for default notification");
            notificationManager.createNotificationChannel(channel);
        }

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, reqCode, i , PendingIntent.FLAG_CANCEL_CURRENT);

        // Intermediate enhancement
        Bitmap picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.friday);
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.directdownload);
        NotificationCompat.BigPictureStyle bigPicture = new NotificationCompat.BigPictureStyle();
        bigPicture.setBigContentTitle("Task Manager Reminder");
        bigPicture.bigPicture(picture);
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(500);
        }

        //Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");
        builder.setContentTitle("Task Manager Reminder");
        DBHelper dbHelper = new DBHelper(context.getApplicationContext());
        ArrayList<Task> data = dbHelper.getTasks();
//        for (int a = 0; a < data.size(); a++) {
//            builder.setContentText(dbHelper.getTasks().get(a).getName());
//
//        }

        builder.setContentText(dbHelper.getTasks().get(data.size() - 1).getName());

        builder.setSmallIcon(android.R.drawable.ic_dialog_info);
        builder.setStyle(bigPicture);
        builder.setLargeIcon(largeIcon);
        builder.setContentIntent(pIntent);
        builder.setAutoCancel(true);
        Notification notification = new Notification();
        notification.ledARGB = 0xff0000ff; // Blue color light flash
        notification.ledOnMS = 2000; // LED is on for 2 second
        notification.ledOffMS = 500; // LED is off for 0.5 second
        notification.flags = Notification.FLAG_SHOW_LIGHTS;
//        notificationManager.notify(0, notification);
        builder.setLights(Color.CYAN, 5000, 5000);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);
        builder.setPriority(Notification.PRIORITY_HIGH);

        notification = builder.build();
        notificationManager.notify(123,notification);
    }
}