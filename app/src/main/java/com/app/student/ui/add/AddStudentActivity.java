package com.app.student.ui.add;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.student.R;
import com.app.student.base.ThemeActivity;
import com.app.student.database.AppDatabase;
import com.app.student.databinding.ActivityAddStudentBinding;
import com.app.student.model.Student;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.app.student.util.ToastUtil.toast;

public class AddStudentActivity extends ThemeActivity {
    ActivityAddStudentBinding addStudentBinding;

    private String studentBirth;
    private String studentGender;
    private String studentHometown;
    private String studentId;
    private String studentMajor;
    private String studentPhone;
    private String studentName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addStudentBinding = ActivityAddStudentBinding.inflate(LayoutInflater.from(this));
        setContentView(addStudentBinding.getRoot());
//        getActionBarLayoutBinding().title.setText("添加学生信息");
        setTitle(R.string.student_name);
        getActionBarLayoutBinding().setting.setText("保存");
        getActionBarLayoutBinding().setting.setVisibility(View.VISIBLE);
        getActionBarLayoutBinding().setting.setOnClickListener(v -> {
            addStudent();
        });
        addStudentBinding.etStudentBirth.setFocusable(false);
        addStudentBinding.etStudentBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间选择器
                new TimePickerBuilder(AddStudentActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        addStudentBinding.etStudentBirth.setText(getTime(date));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})
                        .build().show();
            }
        });
    }


    public void addStudent() {

        studentBirth = addStudentBinding.etStudentBirth.getText().toString();
        int genderButtonId = addStudentBinding.genderRG.getCheckedRadioButtonId();
        studentGender = genderButtonId == R.id.male ? "男" : "女";
        studentHometown = addStudentBinding.etStudentHometown.getText().toString();
        studentId = addStudentBinding.etStudentId.getText().toString();
        studentMajor = addStudentBinding.etStudentMajor.getText().toString();
        studentPhone = addStudentBinding.etStudentPhone.getText().toString();
        studentName = addStudentBinding.etStudentName.getText().toString();
        if (TextUtils.isEmpty(studentBirth)
                || TextUtils.isEmpty(studentGender)
                || TextUtils.isEmpty(studentHometown)
                || TextUtils.isEmpty(studentId)
                || TextUtils.isEmpty(studentMajor)
                || TextUtils.isEmpty(studentPhone)
                || TextUtils.isEmpty(studentName)
        ) {
            ToastUtils.showShort("请填写完整信息");
            return;
        }
        Student student = new Student();
        student.setStudentName(studentName);
        student.setStudentBirth(studentBirth);
        student.setStudentHometown(studentHometown);
        student.setStudentMajor(studentMajor);
        student.setStudentPhone(studentPhone);
        student.setStudentGender(studentGender);
        student.setStudentId(studentId);
        if (AppDatabase.getInstance().studentDao().getStudentById(studentId).size() == 0) {
            AppDatabase.getInstance().studentDao().insertStudent(student);
            toast("保存成功");
            this.finish();
        } else {
            toast("学生信息已存在");
        }
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-HH-mm");
        return format.format(date);
    }

}
