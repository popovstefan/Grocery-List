package php.com.mk.grocerylist.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import php.com.mk.grocerylist.model.GroceryList;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.persistence.dao.GroceryListDao;
import php.com.mk.grocerylist.persistence.dao.MainListDAO;

@Database(entities = {MainList.class, GroceryList.class}, version = 3)
public abstract class ApplicationDatabase extends RoomDatabase {

    /*Primena na Singleton shablon pri kreiranje na instanca od RoomDatabase,
     * spored preporakite od Andorid developers i Room, instanca na RoomDatabase e skapa i poradi toa
     * upotrebivme Singleton */
    private static final String DB_NAME = "app-database";
    private static volatile ApplicationDatabase instance;

    public static synchronized ApplicationDatabase getInstance(Context context) {
        if (instance == null)
            instance = create(context);
        return instance;
    }

    private static ApplicationDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                ApplicationDatabase.class,
                DB_NAME).build();
    }


    public abstract MainListDAO mainListDAO();

    public abstract GroceryListDao groceryListDao();
}
