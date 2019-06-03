package php.com.mk.grocerylist.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import php.com.mk.grocerylist.MainActivity;
import php.com.mk.grocerylist.R;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.ApplicationDatabase;
import php.com.mk.grocerylist.persistence.repository.MainListRepository;

public class NotificationService extends JobIntentService {
    private static final String NOTIFICATION_CONTENT_TITLE = "Grocery deadline approaching";
    private static final String NOTIFICATION_CONTENT_TEXT = "You have a grocery list to shop for soon. Check it out.";
    private static final String NOTIFICATION_CHANNEL_ID = "Notification channel id";
    private static final AtomicInteger notificationId = new AtomicInteger(1);
    private static final int DAYS_UNTIL_DEADLINE = 2;
    private static final String tag = "Notification service";

    public static void enqueueWork(Context context, Intent work) {
        JobIntentService.enqueueWork(context, NotificationService.class, 1, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (deadlineIsNearing()) {
            Log.v(tag, "Entered if");
            createNotificationChannel(this);

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(NOTIFICATION_CONTENT_TITLE)
                    .setContentText(NOTIFICATION_CONTENT_TEXT)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            /* notificationId.get() and not getAndIncrement() to prevent raising the same notification more than once,
                since there is only one type of notification available
             */
            notificationManagerCompat.notify(notificationId.get(), builder.build());
            Log.v(tag, "Exited if");
            // stop the service once the notification is shown
            stopSelf();
        }
    }

    private boolean deadlineIsNearing() {
        Log.v(tag, "Entered deadline");
        LiveData<List<MainList>> lists = new MainListRepository(this)
                .getAll();
        Log.v(tag, "AWKI Lists: [ " + lists.toString() + " ].");
        List<MainList> lsts = ApplicationDatabase.getInstance(this.getApplicationContext())
                .mainListDAO()
                .getThem();
        Log.v(tag, "AWKI Lsts: [ " + lsts.toString() + " ].");
        boolean isNull = lsts.size() > 0;
        Log.v(tag, String.valueOf("AWKI Lstss: [ " + isNull + " ]"));
        if (!isNull) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, DAYS_UNTIL_DEADLINE);
            Date deadlineDate = calendar.getTime();

            for (MainList list : lsts)
                if (list.getListDate().before(deadlineDate))
                    return false;
        }
        return true;
    }

    private void createNotificationChannel(Context context) {
        /*
            Create the NotificationChannel, but only on API 26+ because
            the NotificationChannel class is new and not in the support library
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviours after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
