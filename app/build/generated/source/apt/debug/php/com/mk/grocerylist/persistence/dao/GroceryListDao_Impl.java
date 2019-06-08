package php.com.mk.grocerylist.persistence.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import php.com.mk.grocerylist.model.GroceryList;

@SuppressWarnings("unchecked")
public class GroceryListDao_Impl implements GroceryListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGroceryList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfGroceryList;

  public GroceryListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGroceryList = new EntityInsertionAdapter<GroceryList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `GroceryList`(`id`,`list_id`,`name`,`quantity`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GroceryList value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getListId());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        stmt.bindLong(4, value.getQuantity());
      }
    };
    this.__deletionAdapterOfGroceryList = new EntityDeletionOrUpdateAdapter<GroceryList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `GroceryList` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GroceryList value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insert(GroceryList item) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGroceryList.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(GroceryList item) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfGroceryList.handle(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<GroceryList>> getGroceriesForList(int listId) {
    final String _sql = "select gl.* from GroceryList as gl where gl.list_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    return new ComputableLiveData<List<GroceryList>>() {
      private Observer _observer;

      @Override
      protected List<GroceryList> compute() {
        if (_observer == null) {
          _observer = new Observer("GroceryList") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfListId = _cursor.getColumnIndexOrThrow("list_id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfQuantity = _cursor.getColumnIndexOrThrow("quantity");
          final List<GroceryList> _result = new ArrayList<GroceryList>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final GroceryList _item;
            _item = new GroceryList();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpListId;
            _tmpListId = _cursor.getInt(_cursorIndexOfListId);
            _item.setListId(_tmpListId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            _item.setQuantity(_tmpQuantity);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public List<GroceryList> fetchGroceriesForList(int listId) {
    final String _sql = "select gl.* from GroceryList as gl where gl.list_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, listId);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfListId = _cursor.getColumnIndexOrThrow("list_id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfQuantity = _cursor.getColumnIndexOrThrow("quantity");
      final List<GroceryList> _result = new ArrayList<GroceryList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final GroceryList _item;
        _item = new GroceryList();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpListId;
        _tmpListId = _cursor.getInt(_cursorIndexOfListId);
        _item.setListId(_tmpListId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final int _tmpQuantity;
        _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
        _item.setQuantity(_tmpQuantity);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
