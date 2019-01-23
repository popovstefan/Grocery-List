package php.com.mk.grocerylist.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import php.com.mk.grocerylist.model.MainList;

@Dao
public interface MainListDAO {
    @Insert
    void insert(MainList list);

    //dalii obicen delete ili da napravime kako istorija namesto delete
    //da pravi update na pole kupena na TRUE
    //momentalno raboti so obicen delete pri klik na kopce done
    @Delete
    void delete(MainList list);

    @Query("SELECT * FROM List l ORDER BY l.listDate")
    LiveData<List<MainList>> getAll();
}
