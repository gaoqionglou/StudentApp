package com.app.student.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.app.student.base.CommonActivity;
import com.app.student.common.Constants;
import com.app.student.databinding.ActivityLoginBinding;
import com.app.student.ui.register.RegisterActivity;
import com.app.student.ui.studentlist.StudentListActivity;
import com.app.student.util.SharedPreferencesUtils;

import static com.app.student.common.Constants.REQUEST_CODE_REGISTER;
import static com.app.student.util.ToastUtil.toast;


public class LoginActivity extends CommonActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLoginBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(activityLoginBinding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setCustomActionBar();
        activityLoginBinding.cbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveStudentLoginInfoToCahce();
                } else {
                    SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginId, "");
                    SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginPsw, "");
                }
            }
        });

        loginViewModel.getLoginUser().observe(this, loginUser -> {
            if (loginUser == null) {
                toast("尚未注册，请先注册");
                return;
            }
            Intent intent = new Intent(LoginActivity.this, StudentListActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
            Log.i(Constants.TAG, loginUser.toString());
        });
    }

    private void saveStudentLoginInfoToCahce() {
        String username = activityLoginBinding.studentId.getText().toString().trim();
        String pswd = activityLoginBinding.password.getText().toString().trim();
        SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginId, username);
        SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginPsw, pswd);
    }

    public void clickLogin(View view) {
        String username = activityLoginBinding.studentId.getText().toString().trim();
        String pswd = activityLoginBinding.password.getText().toString().trim();
        SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginId, username);
        SharedPreferencesUtils.saveString(LoginActivity.this, Constants.StudentAppLastLoginPsw, pswd);
        if (TextUtils.isEmpty(username)) {
            toast("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(pswd)) {
            toast("密码不能为空");
            return;
        }
        loginViewModel.login(username, pswd);

    }

    public void clickRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_REGISTER) {
            if (data == null) return;
            activityLoginBinding.studentId.setText(data.getStringExtra("username"));
            activityLoginBinding.password.setText(data.getStringExtra("password"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityLoginBinding.studentId.setText(SharedPreferencesUtils.getString(LoginActivity.this, Constants.StudentAppLastLoginId, ""));
        activityLoginBinding.password.setText(SharedPreferencesUtils.getString(LoginActivity.this, Constants.StudentAppLastLoginPsw, ""));

    }
}