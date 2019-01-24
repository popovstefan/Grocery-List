package php.com.mk.grocerylist.persistence.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.persistence.ApplicationDatabase;

public class GroceryListRepository {
    private Context context;

    public GroceryListRepository(Context context) {
        this.context = context;
    }

    public void insertItem(final GroceryList item) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                ApplicationDatabase.getInstance(context).groceryListDao().insert(item);
                return null;
            }
        }.execute();
    }

    public LiveData<List<GroceryList>> getGroceriesForListId(final int listId) {
        return ApplicationDatabase.getInstance(context)
                .groceryListDao()
                .getGroceriesForList(listId);
    }

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
