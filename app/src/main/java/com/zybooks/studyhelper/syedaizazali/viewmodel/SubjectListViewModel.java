package com.zybooks.studyhelper.syedaizazali.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.zybooks.studyhelper.syedaizazali.model.Subject;
import com.zybooks.studyhelper.syedaizazali.repo.StudyRepository;

import java.util.List;

public class SubjectListViewModel extends AndroidViewModel {
    private final StudyRepository studyRepo;
    public SubjectListViewModel(Application application){
        super(application);
        studyRepo = StudyRepository.getInstance(application.getApplicationContext());
    }
    public LiveData<List<Subject>> getSubjects(){
        return studyRepo.getSubjects();
    }
    public void addSubject(Subject subject){

        studyRepo.addSubject(subject);
    }
    public void deleteSubject(Subject subject){

        studyRepo.deleteSubject(subject);
    }

}