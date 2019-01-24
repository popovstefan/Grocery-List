package php.com.mk.grocerylist.persistence.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.persistence.ApplicationDatabase;

/**
 * Repository class for the groceries belonging in a list.
 */
public class GroceryListRepository {
    private Context context;

    public GroceryListRepository(Context context) {
        this.context = context;
    }

    /**
     * Async task for inserting a grocery item.
     *
     * @param item to be inserted
     */
    public void insertItem(final GroceryList item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).groceryListDao().insert(item);
                return null;
            }
        }.execute();
    }

    /**
     * All groceries associated with a particular list,
     * wrapped in a LiveData object tailored for the Observer design pattern.
     *
     * @param listId is the particular list id for which the groceries are to be return
     * @return the groceries associated with that particular list
     */
    public LiveData<List<GroceryList>> getGroceriesForListId(final int listId) {
        return ApplicationDatabase.getInstance(context)
                .groceryListDao()
                .getGroceriesForList(listId);
    }

    /**
     * Async task for deleting a grocery item
     *
     * @param item to be deleted
     */
    public void deleteItem(final GroceryList item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).groceryListDao().delete(item);
                return null;
            }
        }.execute();
    }
}
