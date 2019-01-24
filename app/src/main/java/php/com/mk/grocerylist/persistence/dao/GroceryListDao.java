package php.com.mk.grocerylist.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;

@Dao
public interface GroceryListDao {

    @Insert
    void insert(GroceryList item);

    @Delete
    void delete(GroceryList item);

    @Query(value = "select gl.* " +
            "from GroceryList as gl " +
            "where gl.list_id = :listId")
    LiveData<List<GroceryList>> getGroceriesForList(int listId);
}
