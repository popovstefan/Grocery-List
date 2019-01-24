package php.com.mk.grocerylist.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.dao.GroceryListDao;
import php.com.mk.grocerylist.persistence.dao.MainListDAO;

/**
 * The database class
 */
@Database(entities = {MainList.class, GroceryList.class}, version = 3)
public abstract class ApplicationDatabase extends RoomDatabase {
    private static final String DB_NAME = "app-database";
    private static volatile ApplicationDatabase instance;

    /**
     * Implements Singleton design pattern to
     * instantiate and return a single database instance.
     *
     * @param context in which the database exists
     * @return that instance
     */
    public static synchronized ApplicationDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    /**
     * Creates a database instance.
     *
     * @param context in which the database exists
     * @return the database instance
     */
    private static ApplicationDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ApplicationDatabase.class,
                DB_NAME).fallbackToDestructiveMigration()  //bidejki pravese problemi
                .build();
    }

    // The data access objects associated with the entities in this database
    public abstract MainListDAO mainListDAO();

    public abstract GroceryListDao groceryListDao();
}
