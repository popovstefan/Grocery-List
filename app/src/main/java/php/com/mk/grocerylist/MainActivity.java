package php.com.mk.grocerylist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.notification.NotificationReceiver;
import php.com.mk.grocerylist.persistence.repository.MainListRepository;
import php.com.mk.grocerylist.recyclerAdapters.MainListRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_LIST_ACTIVITY_CODE = 1000;
    public static final String NOTIFICATION_ACTION = "Notification intent action string";
    RecyclerView recyclerView;
    MainListRecyclerAdapter mainListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    List<MainList> list;
    MainListRepository mainListRepository;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void startAlarm() {
        Intent alarmIntent = new Intent(MainActivity.this, NotificationReceiver.class);
        alarmIntent.setAction(MainActivity.NOTIFICATION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int minuteInMillis = 1000 * 60 / 1440; // one minute in millis
        int interval = minuteInMillis * 1440;
        // Set the alarm to start at 12:00 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        // Repeat every day (1,440 minutes interval)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), interval, pendingIntent);
        Toast.makeText(MainActivity.this, "Pocna repeat", Toast.LENGTH_LONG).show();
    }

    /**
     * Connecting the elements defined in the layout with the
     * corresponding ones in this activity so that they can
     * be accessed in the program code.
     */
    public void initUI() {
        list = null;
        mainListRepository = new MainListRepository(this);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mainListRecyclerAdapter = new MainListRecyclerAdapter(this, list);
        recyclerView.setAdapter(mainListRecyclerAdapter);
        LiveData<List<MainList>> ldItems = mainListRepository.getAll();
        ldItems.observe(this, new Observer<List<MainList>>() {
            /**
             * Method which is to be activated then the observed list of item changes.
             * A direct implementation of the observer design pattern.
             * @param items which are to be updated, if they are not empty or null
             */
            @Override
            public void onChanged(@Nullable List<MainList> items) {
                if (items == null || items.size() == 0) {
                    Toast.makeText(getApplicationContext(), "All groceries are bought!", Toast.LENGTH_SHORT).show();
                } else {
                    mainListRecyclerAdapter.updateMainList(items);
                }
            }
        });
        startAlarm();
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            /**
             * Creates an explicit intent with which starts
             * an activity for creating a new grocery list.
             *
             * @param view unused
             */

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewListActivity.class);
                startActivityForResult(intent, NEW_LIST_ACTIVITY_CODE);
            }
        });
    }


    /**
     * Adds the newly created grocery list to the
     * repository i.e. writes it into the database.
     * Is activated after the new list activity finishes.
     * The newly created grocery list is found in that activity's intent data.
     *
     * @param requestCode is the code with which the new grocery list activity was started
     * @param resultCode  is the new grocery list activity's result code
     * @param data        is the intent the new grocery list activity gives back after finishing
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LIST_ACTIVITY_CODE
                && resultCode == Activity.RESULT_OK
                & data != null) {
            MainList result = (MainList) data.getSerializableExtra("newList");
            mainListRepository.insertItem(result);
        }
    }
}
