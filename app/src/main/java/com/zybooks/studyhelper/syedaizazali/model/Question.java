package com.zybooks.studyhelper.syedaizazali.model;
 import androidx.room.Entity;
 import androidx.room.ColumnInfo;
 import androidx.room.ForeignKey;
 import androidx.room.PrimaryKey;

 import static androidx.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = Subject.class, parentColumns = "id",
        childColumns = "subject_id", onDelete = CASCADE))
public class Question {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @ColumnInfo(name = "text")
    private String mText;

    @ColumnInfo(name = "answer")
    private String mAnswer;

    @ColumnInfo(name = "subject_id")
    private long mSubjectId;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        this.mAnswer = answer;
    }

    public long getSubjectId() {
        return mSubjectId;
    }

    public void setSubjectId(long subjectId) {
        this.mSubjectId = subjectId;
    }
}
