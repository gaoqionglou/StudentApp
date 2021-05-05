package com.app.student.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.student.database.AppDatabase;
import com.app.student.model.User;

import java.util.List;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<User> loginUser = new MutableLiveData<>();

    public MutableLiveData<User> getLoginUser() {
        return loginUser;
    }

    public void login(String id, String pwd) {
        List<User> result = AppDatabase.getInstance().userDao().getUserByIdAndPsw(id, pwd);
        if (result != null && !result.isEmpty()) {
            loginUser.setValue(result.get(0));
        } else {
            loginUser.setValue(null);
        }
    }

}
