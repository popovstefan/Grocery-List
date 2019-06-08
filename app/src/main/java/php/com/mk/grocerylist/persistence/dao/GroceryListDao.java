package php.com.mk.grocerylist.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import php.com.mk.grocerylist.model.GroceryList;

/**
 * Data access object interface for the groceries belonging to a list
 */
@Dao
public interface GroceryListDao {

    /**
     * Inserts the grocery in the database
     *
     * @param item to be inserted
     */
    @Insert
    void insert(GroceryList item);

    /**
     * Deletes a grocery from the database
     *
     * @param item to be deleted
     */
    @Delete
    void delete(GroceryList item);

    /**
     * Self explanatory
     *
     * @param listId the list id
     * @return a list of groceries belonging in the list with that id
     */
    @Query(value = "select gl.* " +
            "from GroceryList as gl " +
            "where gl.list_id = :listId")
    LiveData<List<GroceryList>> getGroceriesForList(int listId);

    /**
     * Self explanatory
     *
     * @param listId the list id
     * @return a list of groceries belonging in the list with that id
     */
    @Query(value = "select gl.* " +
            "from GroceryList as gl " +
            "where gl.list_id = :listId")
    List<GroceryList> fetchGroceriesForList(int listId);
}
