package php.com.mk.grocerylist.persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import php.com.mk.grocerylist.persistence.dao.GroceryListDao;
import php.com.mk.grocerylist.persistence.dao.GroceryListDao_Impl;
import php.com.mk.grocerylist.persistence.dao.MainListDAO;
import php.com.mk.grocerylist.persistence.dao.MainListDAO_Impl;

@SuppressWarnings("unchecked")
public class ApplicationDatabase_Impl extends ApplicationDatabase {
  private volatile MainListDAO _mainListDAO;

  private volatile GroceryListDao _groceryListDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `List` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `listName` TEXT, `listDate` TEXT, `priority` TEXT, `location` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `GroceryList` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `list_id` INTEGER NOT NULL, `name` TEXT, `quantity` INTEGER NOT NULL, FOREIGN KEY(`list_id`) REFERENCES `List`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"29c23472084b1446e8b5783f4e1f68a2\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `List`");
        _db.execSQL("DROP TABLE IF EXISTS `GroceryList`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsList = new HashMap<String, TableInfo.Column>(5);
        _columnsList.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsList.put("listName", new TableInfo.Column("listName", "TEXT", false, 0));
        _columnsList.put("listDate", new TableInfo.Column("listDate", "TEXT", false, 0));
        _columnsList.put("priority", new TableInfo.Column("priority", "TEXT", false, 0));
        _columnsList.put("location", new TableInfo.Column("location", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysList = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoList = new TableInfo("List", _columnsList, _foreignKeysList, _indicesList);
        final TableInfo _existingList = TableInfo.read(_db, "List");
        if (! _infoList.equals(_existingList)) {
          throw new IllegalStateException("Migration didn't properly handle List(php.com.mk.grocerylist.model.MainList).\n"
                  + " Expected:\n" + _infoList + "\n"
                  + " Found:\n" + _existingList);
        }
        final HashMap<String, TableInfo.Column> _columnsGroceryList = new HashMap<String, TableInfo.Column>(4);
        _columnsGroceryList.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsGroceryList.put("list_id", new TableInfo.Column("list_id", "INTEGER", true, 0));
        _columnsGroceryList.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsGroceryList.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGroceryList = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysGroceryList.add(new TableInfo.ForeignKey("List", "CASCADE", "NO ACTION",Arrays.asList("list_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGroceryList = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGroceryList = new TableInfo("GroceryList", _columnsGroceryList, _foreignKeysGroceryList, _indicesGroceryList);
        final TableInfo _existingGroceryList = TableInfo.read(_db, "GroceryList");
        if (! _infoGroceryList.equals(_existingGroceryList)) {
          throw new IllegalStateException("Migration didn't properly handle GroceryList(php.com.mk.grocerylist.model.GroceryList).\n"
                  + " Expected:\n" + _infoGroceryList + "\n"
                  + " Found:\n" + _existingGroceryList);
        }
      }
    }, "29c23472084b1446e8b5783f4e1f68a2", "13f871f83e224a1084ea5c58a7ad3683");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "List","GroceryList");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `List`");
      _db.execSQL("DELETE FROM `GroceryList`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public MainListDAO mainListDAO() {
    if (_mainListDAO != null) {
      return _mainListDAO;
    } else {
      synchronized(this) {
        if(_mainListDAO == null) {
          _mainListDAO = new MainListDAO_Impl(this);
        }
        return _mainListDAO;
      }
    }
  }

  @Override
  public GroceryListDao groceryListDao() {
    if (_groceryListDao != null) {
      return _groceryListDao;
    } else {
      synchronized(this) {
        if(_groceryListDao == null) {
          _groceryListDao = new GroceryListDao_Impl(this);
        }
        return _groceryListDao;
      }
    }
  }
}
