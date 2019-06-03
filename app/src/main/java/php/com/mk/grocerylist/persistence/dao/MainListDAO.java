package php.com.mk.grocerylist.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import php.com.mk.grocerylist.model.MainList;

/**
 * Data access object interface for the grocery lists
 */
@Dao
public interface MainListDAO {
    /**
     * Inserts the list in the database
     *
     * @param list to be inserted
     */
    @Insert
    void insert(MainList list);

    /**
     * Delets the list from the database
     *
     * @param list to be deleted
     */
    @Delete
    void delete(MainList list);

    /**
     * Self explanatory
     *
     * @return all grocery lists ordered by their date
     */
    @Query("SELECT * FROM List l ORDER BY l.listDate")
    LiveData<List<MainList>> getAll();

    @Query("SELECT * FROM List l ORDER BY l.listDate")
    List<MainList> getThem();
}
