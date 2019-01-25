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
import java.util.Date;
import java.util.List;
import java.util.Set;
import php.com.mk.grocerylist.model.MainList;
import php.com.mk.grocerylist.utils.TimeDateHelper;

@SuppressWarnings("unchecked")
public class MainListDAO_Impl implements MainListDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMainList;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMainList;

  public MainListDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMainList = new EntityInsertionAdapter<MainList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `List`(`id`,`listName`,`listDate`,`priority`,`location`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MainList value) {
        stmt.bindLong(1, value.getId());
        if (value.getListName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getListName());
        }
        final String _tmp;
        _tmp = TimeDateHelper.dateToTimestamp(value.getListDate());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, _tmp);
        }
        if (value.getPriority() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPriority());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getLocation());
        }
      }
    };
    this.__deletionAdapterOfMainList = new EntityDeletionOrUpdateAdapter<MainList>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `List` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MainList value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public void insert(MainList list) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMainList.insert(list);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(MainList list) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMainList.handle(list);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<MainList>> getAll() {
    final String _sql = "SELECT * FROM List l ORDER BY l.listDate";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<MainList>>() {
      private Observer _observer;

      @Override
      protected List<MainList> compute() {
        if (_observer == null) {
          _observer = new Observer("List") {
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
          final int _cursorIndexOfListName = _cursor.getColumnIndexOrThrow("listName");
          final int _cursorIndexOfListDate = _cursor.getColumnIndexOrThrow("listDate");
          final int _cursorIndexOfPriority = _cursor.getColumnIndexOrThrow("priority");
          final int _cursorIndexOfLocation = _cursor.getColumnIndexOrThrow("location");
          final List<MainList> _result = new ArrayList<MainList>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MainList _item;
            _item = new MainList();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpListName;
            _tmpListName = _cursor.getString(_cursorIndexOfListName);
            _item.setListName(_tmpListName);
            final Date _tmpListDate;
            final String _tmp;
            _tmp = _cursor.getString(_cursorIndexOfListDate);
            _tmpListDate = TimeDateHelper.fromTimestamp(_tmp);
            _item.setListDate(_tmpListDate);
            final String _tmpPriority;
            _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            _item.setPriority(_tmpPriority);
            final String _tmpLocation;
            _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            _item.setLocation(_tmpLocation);
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
