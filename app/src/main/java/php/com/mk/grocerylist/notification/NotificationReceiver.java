package php.com.mk.grocerylist.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import php.com.mk.grocerylist.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null)
            Log.v("Notification receiver", intent.getAction());
            if (intent.getAction().equals(MainActivity.NOTIFICATION_ACTION)) {
//                Toast.makeText(context, "SHOULD START NOTIFICATION", Toast.LENGTH_LONG).show();
                NotificationService.enqueueWork(context, new Intent());
            }
    }
}
