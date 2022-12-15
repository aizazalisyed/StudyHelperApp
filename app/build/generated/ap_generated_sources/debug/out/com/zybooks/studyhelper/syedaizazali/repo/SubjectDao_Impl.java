package com.zybooks.studyhelper.syedaizazali.repo;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.zybooks.studyhelper.syedaizazali.model.Subject;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SubjectDao_Impl implements SubjectDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Subject> __insertionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter<Subject> __deletionAdapterOfSubject;

  private final EntityDeletionOrUpdateAdapter<Subject> __updateAdapterOfSubject;

  public SubjectDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubject = new EntityInsertionAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Subject` (`id`,`text`,`updated`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        stmt.bindLong(1, value.getId());
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        stmt.bindLong(3, value.getUpdateTime());
      }
    };
    this.__deletionAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Subject` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfSubject = new EntityDeletionOrUpdateAdapter<Subject>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Subject` SET `id` = ?,`text` = ?,`updated` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subject value) {
        stmt.bindLong(1, value.getId());
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        stmt.bindLong(3, value.getUpdateTime());
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public long addSubject(final Subject subject) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfSubject.insertAndReturnId(subject);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteSubject(final Subject subject) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubject.handle(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateSubject(final Subject subject) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSubject.handle(subject);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<Subject> getSubject(final long id) {
    final String _sql = "SELECT * FROM Subject WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Subject"}, false, new Callable<Subject>() {
      @Override
      public Subject call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfMUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updated");
          final Subject _result;
          if(_cursor.moveToFirst()) {
            final String _tmpMText;
            if (_cursor.isNull(_cursorIndexOfMText)) {
              _tmpMText = null;
            } else {
              _tmpMText = _cursor.getString(_cursorIndexOfMText);
            }
            _result = new Subject(_tmpMText);
            final long _tmpMId;
            _tmpMId = _cursor.getLong(_cursorIndexOfMId);
            _result.setId(_tmpMId);
            final long _tmpMUpdateTime;
            _tmpMUpdateTime = _cursor.getLong(_cursorIndexOfMUpdateTime);
            _result.setUpdateTime(_tmpMUpdateTime);
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
    });
  }

  @Override
  public LiveData<List<Subject>> getSubjects() {
    final String _sql = "SELECT * FROM Subject ORDER BY text COLLATE NOCASE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Subject"}, false, new Callable<List<Subject>>() {
      @Override
      public List<Subject> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfMUpdateTime = CursorUtil.getColumnIndexOrThrow(_cursor, "updated");
          final List<Subject> _result = new ArrayList<Subject>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Subject _item;
            final String _tmpMText;
            if (_cursor.isNull(_cursorIndexOfMText)) {
              _tmpMText = null;
            } else {
              _tmpMText = _cursor.getString(_cursorIndexOfMText);
            }
            _item = new Subject(_tmpMText);
            final long _tmpMId;
            _tmpMId = _cursor.getLong(_cursorIndexOfMId);
            _item.setId(_tmpMId);
            final long _tmpMUpdateTime;
            _tmpMUpdateTime = _cursor.getLong(_cursorIndexOfMUpdateTime);
            _item.setUpdateTime(_tmpMUpdateTime);
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
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
