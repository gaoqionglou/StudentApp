package com.app.student.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.app.student.R;
import com.app.student.base.ThemeActivity;
import com.app.student.databinding.ActiviyRegisterBinding;
import com.app.student.model.Student;
import com.app.student.model.User;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.app.student.util.ToastUtil.toast;


public class RegisterActivity extends ThemeActivity {
    private ActiviyRegisterBinding activiyRegisterBinding;
    private RegisterViewModel registerViewModel;
    private String studentId, studentPwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activiyRegisterBinding = ActiviyRegisterBinding.inflate(LayoutInflater.from(this));
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        setTitle("注册");
        setContentView(activiyRegisterBinding.getRoot());
        activiyRegisterBinding.etBirth.setFocusable(false);
        activiyRegisterBinding.etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                new TimePickerBuilder(RegisterActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        activiyRegisterBinding.etBirth.setText(format.format(date));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .build().show();
            }
        });
        activiyRegisterBinding.btnRegister.setOnClickListener(v -> {
            studentId = activiyRegisterBinding.etStudentId.getText().toString().trim();
            studentPwd = activiyRegisterBinding.etPwd.getText().toString().trim();
            String studentName = activiyRegisterBinding.etStudentName.getText().toString().trim();
            int genderButtonId = activiyRegisterBinding.genderRG.getCheckedRadioButtonId();
            String gender = genderButtonId == R.id.male ? "男" : "女";
            String hometown = activiyRegisterBinding.etHometown.getText().toString().trim();
            String mobile = activiyRegisterBinding.etMobile.getText().toString().trim();
            String major = activiyRegisterBinding.etMajor.getText().toString().trim();
            String birth = activiyRegisterBinding.etBirth.getText().toString().trim();

            if (TextUtils.isEmpty(studentId) ||
                    TextUtils.isEmpty(studentPwd) ||
                    TextUtils.isEmpty(studentName) ||
                    TextUtils.isEmpty(gender) ||
                    TextUtils.isEmpty(hometown) ||
                    TextUtils.isEmpty(mobile) ||
                    TextUtils.isEmpty(major) ||
                    TextUtils.isEmpty(birth)
            ) {
                toast("请填写完整你的信息再进行注册");
                return;
            }
            User user = new User();
            user.setStudentId(studentId);
            user.setStudentPsw(studentPwd);

            Student student = new Student();
            student.setStudentId(studentId);
            student.setStudentGender(gender);
            student.setStudentPhone(mobile);
            student.setStudentMajor(major);
            student.setStudentHometown(hometown);
            student.setStudentBirth(birth);
            student.setStudentName(studentName);
            registerViewModel.register(user, student);
        });

        registerViewModel.getRegisterResult().observe(this, result -> {
            if (result) {
                toast("注册成功");
                Intent localIntent = new Intent();
                localIntent.putExtra("username", studentId);
                localIntent.putExtra("password", studentPwd);
                RegisterActivity.this.setResult(RESULT_OK, localIntent);
                finish();
            }
        });
    }
}
