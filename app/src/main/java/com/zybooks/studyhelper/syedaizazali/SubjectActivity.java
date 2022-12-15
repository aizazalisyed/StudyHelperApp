package com.zybooks.studyhelper.syedaizazali;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zybooks.studyhelper.syedaizazali.model.Subject;
import com.zybooks.studyhelper.syedaizazali.viewmodel.SubjectListViewModel;

import java.util.Comparator;
import java.util.List;

public class SubjectActivity extends AppCompatActivity
        implements SubjectDialogFragment.OnSubjectEnteredListener {
    private SubjectAdapter mSubjectAdapter;
    private RecyclerView mRecyclerView;
    private int[] mSubjectColors;
    private SubjectListViewModel mSubjectListViewModel;
    private Boolean mLoadSubjectList = true;
    private Subject mSelectedSubject;
    private int mSelectedSubjectPosition = RecyclerView.NO_POSITION;
    private ActionMode mActionMode = null;

    public enum SubjectSortOrder{
        ALPHABETIC, NEW_FIRST, OLD_FIRST
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        mSubjectListViewModel = new ViewModelProvider(this).get(SubjectListViewModel.class);
        mSubjectColors = getResources().getIntArray(R.array.subjectColors);
        findViewById(R.id.add_subject_button).setOnClickListener(view -> addSubjectClick());

        mRecyclerView = findViewById(R.id.subject_recycler_view);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        //updateUI(mSubjectListViewModel.getSubjects());
        mSubjectListViewModel.getSubjects().observe(this, subjects -> {
            if (mLoadSubjectList){
                updateUI(subjects);
            }
        });
    }
    private void updateUI(List<Subject> subjectList){
        mSubjectAdapter = new SubjectAdapter(subjectList);
        mSubjectAdapter.setSortOrder(getSettingsSortOrder());
        mRecyclerView.setAdapter(mSubjectAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.subject_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }else if(item.getItemId() == R.id.import_questions){
            Intent intent = new Intent(this, ImportActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSubjectEntered(String subjectText) {
        if(subjectText.length() > 0){
            Subject subject = new Subject(subjectText);
            mLoadSubjectList = false;
            mSubjectListViewModel.addSubject(subject);
            mSubjectAdapter.addSubject(subject);
            Toast.makeText(this, "Added " + subjectText, Toast.LENGTH_SHORT).show();
        }
    }
    private void addSubjectClick(){
        SubjectDialogFragment dialog = new SubjectDialogFragment();
        dialog.show(getSupportFragmentManager(), "subjectDialog");
    }
    private SubjectSortOrder getSettingsSortOrder(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderPref = sharedPrefs.getString("subject_order", "alpha");
        switch(sortOrderPref){
            case "alpha": return SubjectSortOrder.ALPHABETIC;
            case "new_first": return SubjectSortOrder.NEW_FIRST;
            default: return SubjectSortOrder.OLD_FIRST;
        }
    }
    private class SubjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener{
        private Subject mSubject;
        private final TextView mSubjectTextView;
        public SubjectHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.recycler_view_items, parent, false));
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            mSubjectTextView = itemView.findViewById(R.id.subject_text_view);

        }
        public void bind(Subject subject, int position){
            mSubject = subject;
            mSubjectTextView.setText(subject.getText());
            if (mSelectedSubjectPosition == position){
                mSubjectTextView.setBackgroundColor(Color.RED);
            }
            else{
                int colorIndex = subject.getText().length() % mSubjectColors.length;
                mSubjectTextView.setBackgroundColor(mSubjectColors[colorIndex]);
            }

        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(SubjectActivity.this,QuestionActivity.class);
            intent.putExtra(QuestionActivity.EXTRA_SUBJECT_ID, mSubject.getId());
            intent.putExtra(QuestionActivity.EXTRA_SUBJECT_TEXT, mSubject.getText());
            startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            if (mActionMode != null){
                return false;
            }
            mSelectedSubject = mSubject;
            mSelectedSubjectPosition = getAbsoluteAdapterPosition();
            mSubjectAdapter.notifyItemChanged(mSelectedSubjectPosition);
            mActionMode = SubjectActivity.this.startActionMode(mActionModeCallback);
            return true;
        }
    }
    private final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete){
                mLoadSubjectList = false;
                mSubjectListViewModel.deleteSubject(mSelectedSubject);
                mSubjectAdapter.removeSubject(mSelectedSubject);

                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
            mSubjectAdapter.notifyItemChanged(mSelectedSubjectPosition);
            mSelectedSubjectPosition = RecyclerView.NO_POSITION;
        }
    };
    private class SubjectAdapter extends RecyclerView.Adapter<SubjectHolder>{
        private final List<Subject> mSubjectList;
        public SubjectAdapter(List<Subject> subjects){
            mSubjectList = subjects;
        }


        public void setSortOrder(SubjectSortOrder sortOrder){
            switch (sortOrder){
                case ALPHABETIC:
                    mSubjectList.sort(Comparator.comparing(Subject::getText));
                    break;
                case NEW_FIRST:
                    mSubjectList.sort(Comparator.comparing(Subject::getUpdateTime).reversed());
                    break;
                default:
                    mSubjectList.sort(Comparator.comparing(Subject::getUpdateTime));
            }
        }
        public void addSubject(Subject subject){
            mSubjectList.add(0, subject);
            notifyItemInserted(0);
            mRecyclerView.scrollToPosition(0);
        }
        public void removeSubject(Subject subject){
            int index= mSubjectList.indexOf(subject);
            if (index >= 0){
                mSubjectList.remove(index);
                notifyItemRemoved(index);
            }
        }
        @NonNull
        @Override
        public SubjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater= LayoutInflater.from(getApplicationContext());
            return new SubjectHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectHolder holder, int position) {
            holder.bind(mSubjectList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mSubjectList.size();
        }
    }
}