package com.zybooks.studyhelper.syedaizazali;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.zybooks.studyhelper.syedaizazali.model.Question;
import com.zybooks.studyhelper.syedaizazali.viewmodel.QuestionDetailViewModel;

public class QuestionEditActivity extends AppCompatActivity {
    public static final String EXTRA_QUESTION_ID = "com.zybooks.studyhelper.syedaizazali.question_id";
    public static final String EXTRA_SUBJECT_ID = "com.zybooks.studyhelper.syedaizazali.subject_id";

    private QuestionDetailViewModel mQuestionDetailViewModel;
    private EditText mQuestionEditText;
    private EditText mAnswerEditText;
    private long mQuestionId;
    private Question mQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_edit);

        mQuestionEditText = findViewById(R.id.question_edit_text);
        mAnswerEditText = findViewById(R.id.answer_edit_text);

        findViewById(R.id.save_button).setOnClickListener(view -> saveButtonClick());

        Intent intent = getIntent();
        mQuestionId = intent.getLongExtra(EXTRA_QUESTION_ID, -1);
        mQuestionDetailViewModel = new ViewModelProvider(this)
                .get(QuestionDetailViewModel.class);
        if (mQuestionId == -1){
            mQuestion = new Question();
            mQuestion.setSubjectId(intent.getLongExtra(EXTRA_SUBJECT_ID, 0));
            setTitle(R.string.add_question);
        }else{
            mQuestionDetailViewModel.loadQuestion(mQuestionId);
            mQuestionDetailViewModel.questionLiveData.observe(this,
                    question -> {
                        mQuestion = question;
                        updateUI();
                    });
        }
    }
    private void updateUI(){
        mQuestionEditText.setText(mQuestion.getText());
        mAnswerEditText.setText(mQuestion.getAnswer());
    }
    private void saveButtonClick(){
        mQuestion.setText(mQuestionEditText.getText().toString());
        mQuestion.setAnswer(mAnswerEditText.getText().toString());

        if(mQuestionId == -1){
            mQuestionDetailViewModel.addQuestion(mQuestion);
        }
        else{
            mQuestionDetailViewModel.updateQuestion(mQuestion);
        }
        setResult(RESULT_OK);
        finish();
    }
}