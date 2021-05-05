package com.app.student.ui.register;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.student.database.AppDatabase;
import com.app.student.model.Student;
import com.app.student.model.User;

public class RegisterViewModel extends ViewModel {
    private MutableLiveData<Boolean> result = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> getRegisterResult() {
        return result;
    }

    public void register(User user, Student student) {
        AppDatabase.getInstance().userDao().insertUser(user);
        AppDatabase.getInstance().studentDao().insertStudent(student);
        result.setValue(true);
    }

}
