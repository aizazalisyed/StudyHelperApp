package com.zybooks.studyhelper.syedaizazali.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.zybooks.studyhelper.syedaizazali.model.Subject;
import com.zybooks.studyhelper.syedaizazali.repo.StudyRepository;

import java.util.List;

public class ImportViewModel extends AndroidViewModel {
    public MutableLiveData<String> importedSubject;
    public MutableLiveData<List<Subject>> fetchedSubjectList;
    private final StudyRepository mStudyRepo;
    public ImportViewModel(Application application){
        super(application);
        mStudyRepo = StudyRepository.getInstance(application.getApplicationContext());
        importedSubject = mStudyRepo.importedSubject;
        fetchedSubjectList = mStudyRepo.fetchedSubjectList;
    }
    public void addSubject(Subject subject){
        mStudyRepo.addSubject(subject);
        mStudyRepo.fetchQuestions(subject);
    }
    public void fetchSubjects(){
        mStudyRepo.fetchSubjects();
    }

}