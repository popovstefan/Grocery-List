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

@SuppressWarnings("unchecked")
public class GroceriesDAO_Impl implements GroceriesDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGroceries;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfGroceries;

  public GroceriesDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGroceries = new EntityInsertionAdapter<Groceries>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Groceries`(`id`,`name`,`category`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Groceries value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
      }
    };
    this.__updateAdapterOfGroceries = new EntityDeletionOrUpdateAdapter<Groceries>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Groceries` SET `id` = ?,`name` = ?,`category` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Groceries value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCategory());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public void insert(Groceries groceries) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGroceries.insert(groceries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Groceries groceries) {
    __db.beginTransaction();
    try {
      __updateAdapterOfGroceries.handle(groceries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<Groceries> get(int id) {
    final String _sql = "select * from Groceries where id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return new ComputableLiveData<Groceries>() {
      private Observer _observer;

      @Override
      protected Groceries compute() {
        if (_observer == null) {
          _observer = new Observer("Groceries") {
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
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
          final Groceries _result;
          if(_cursor.moveToFirst()) {
            _result = new Groceries();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _result.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _result.setName(_tmpName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _result.setCategory(_tmpCategory);
          } else {
            _result = null;
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
  public LiveData<List<Groceries>> getAll() {
    final String _sql = "select * from Groceries order by id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Groceries>>() {
      private Observer _observer;

      @Override
      protected List<Groceries> compute() {
        if (_observer == null) {
          _observer = new Observer("Groceries") {
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
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfCategory = _cursor.getColumnIndexOrThrow("category");
          final List<Groceries> _result = new ArrayList<Groceries>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Groceries _item;
            _item = new Groceries();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item.setCategory(_tmpCategory);
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
}
