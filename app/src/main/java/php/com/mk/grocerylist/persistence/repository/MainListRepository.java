package php.com.mk.grocerylist.persistence.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.ApplicationDatabase;

public class MainListRepository {
    private Context context;

    public MainListRepository(Context context) {
        this.context = context;
    }

    public void insertItem(final MainList list) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).mainListDAO().insert(list);
                return null;
            }

        }.execute();
    }

    public LiveData<List<MainList>> getAll() {
        return ApplicationDatabase.getInstance(context).mainListDAO().getAll();
    }

    public void deleteItem(final MainList mainList) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).mainListDAO().delete(mainList);
                return null;
            }
        }.execute();
    }
}
