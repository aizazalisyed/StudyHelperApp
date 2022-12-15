package com.zybooks.studyhelper.syedaizazali.repo;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.zybooks.studyhelper.syedaizazali.model.Question;
import com.zybooks.studyhelper.syedaizazali.model.Subject;

@Database(entities = {Question.class, Subject.class}, version = 1)
public abstract class StudyDatabase  extends RoomDatabase {
    public abstract QuestionDao questionDao();
    public abstract SubjectDao subjectDao();
}
