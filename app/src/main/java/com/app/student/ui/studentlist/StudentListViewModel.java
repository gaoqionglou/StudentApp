package com.app.student.ui.studentlist;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.student.database.AppDatabase;
import com.app.student.model.Student;

import java.util.List;

public class StudentListViewModel extends ViewModel {
    private MutableLiveData<List<Student>> result = new MutableLiveData<List<Student>>();

    public MutableLiveData<List<Student>> getStudentList() {
        return result;
    }

    public void queryAllStudent() {
        List<Student> students = AppDatabase.getInstance().studentDao().getStudentClass();
        if (students != null && !students.isEmpty()) {
            result.setValue(students);
        }
    }

}
