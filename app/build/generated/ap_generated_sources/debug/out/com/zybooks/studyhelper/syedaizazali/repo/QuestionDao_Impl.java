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
import com.zybooks.studyhelper.syedaizazali.model.Question;
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
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  private final EntityDeletionOrUpdateAdapter<Question> __deletionAdapterOfQuestion;

  private final EntityDeletionOrUpdateAdapter<Question> __updateAdapterOfQuestion;

  public QuestionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `Question` (`id`,`text`,`answer`,`subject_id`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getId());
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        if (value.getAnswer() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAnswer());
        }
        stmt.bindLong(4, value.getSubjectId());
      }
    };
    this.__deletionAdapterOfQuestion = new EntityDeletionOrUpdateAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Question` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfQuestion = new EntityDeletionOrUpdateAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Question` SET `id` = ?,`text` = ?,`answer` = ?,`subject_id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getId());
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        if (value.getAnswer() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAnswer());
        }
        stmt.bindLong(4, value.getSubjectId());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public long addQuestion(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfQuestion.insertAndReturnId(question);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteQuestion(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfQuestion.handle(question);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateQuestion(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfQuestion.handle(question);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<Question> getQuestion(final long id) {
    final String _sql = "SELECT * FROM Question WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Question"}, false, new Callable<Question>() {
      @Override
      public Question call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfMAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfMSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subject_id");
          final Question _result;
          if(_cursor.moveToFirst()) {
            _result = new Question();
            final long _tmpMId;
            _tmpMId = _cursor.getLong(_cursorIndexOfMId);
            _result.setId(_tmpMId);
            final String _tmpMText;
            if (_cursor.isNull(_cursorIndexOfMText)) {
              _tmpMText = null;
            } else {
              _tmpMText = _cursor.getString(_cursorIndexOfMText);
            }
            _result.setText(_tmpMText);
            final String _tmpMAnswer;
            if (_cursor.isNull(_cursorIndexOfMAnswer)) {
              _tmpMAnswer = null;
            } else {
              _tmpMAnswer = _cursor.getString(_cursorIndexOfMAnswer);
            }
            _result.setAnswer(_tmpMAnswer);
            final long _tmpMSubjectId;
            _tmpMSubjectId = _cursor.getLong(_cursorIndexOfMSubjectId);
            _result.setSubjectId(_tmpMSubjectId);
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
  public LiveData<List<Question>> getQuestions(final long subjectId) {
    final String _sql = "SELECT * FROM Question WHERE subject_id = ? ORDER BY id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, subjectId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"Question"}, false, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfMAnswer = CursorUtil.getColumnIndexOrThrow(_cursor, "answer");
          final int _cursorIndexOfMSubjectId = CursorUtil.getColumnIndexOrThrow(_cursor, "subject_id");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            _item = new Question();
            final long _tmpMId;
            _tmpMId = _cursor.getLong(_cursorIndexOfMId);
            _item.setId(_tmpMId);
            final String _tmpMText;
            if (_cursor.isNull(_cursorIndexOfMText)) {
              _tmpMText = null;
            } else {
              _tmpMText = _cursor.getString(_cursorIndexOfMText);
            }
            _item.setText(_tmpMText);
            final String _tmpMAnswer;
            if (_cursor.isNull(_cursorIndexOfMAnswer)) {
              _tmpMAnswer = null;
            } else {
              _tmpMAnswer = _cursor.getString(_cursorIndexOfMAnswer);
            }
            _item.setAnswer(_tmpMAnswer);
            final long _tmpMSubjectId;
            _tmpMSubjectId = _cursor.getLong(_cursorIndexOfMSubjectId);
            _item.setSubjectId(_tmpMSubjectId);
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
