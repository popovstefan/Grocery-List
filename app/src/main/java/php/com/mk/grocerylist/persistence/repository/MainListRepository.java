package php.com.mk.grocerylist.persistence.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.ApplicationDatabase;

/**
 * Repository class for the grocery lists.
 */
public class MainListRepository {
    private Context context;

    public MainListRepository(Context context) {
        this.context = context;
    }

    /**
     * Async task for inserting a new grocery list.
     *
     * @param list to be inserted
     */
    public void insertItem(final MainList list) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).mainListDAO().insert(list);
                return null;
            }

        }.execute();
    }

    /**
     * All grocery lists, wrapped in a LiveData object
     * tailored for the Observer design pattern.
     *
     * @return list of all grocery lists
     */
    public LiveData<List<MainList>> getAll() {
        return ApplicationDatabase.getInstance(context).mainListDAO().getAll();
    }

    /**
     * Async task for deleting a grocery list.
     *
     * @param mainList to be deleted
     */
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
