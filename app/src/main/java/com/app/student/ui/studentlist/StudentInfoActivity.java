package com.app.student.ui.studentlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.app.student.R;
import com.app.student.base.ThemeActivity;
import com.app.student.database.AppDatabase;
import com.app.student.databinding.ActivityStudentInfoBinding;
import com.app.student.model.Student;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.app.student.util.ToastUtil.toast;

public class StudentInfoActivity extends ThemeActivity {
    private ActivityStudentInfoBinding studentInfoBinding;
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
        studentInfoBinding = ActivityStudentInfoBinding.inflate(LayoutInflater.from(this));
        setContentView(studentInfoBinding.getRoot());
        setTitle(R.string.student_name);
//        getActionBarLayoutBinding().title.setText("学生信息编辑");
        getActionBarLayoutBinding().setting.setText("提交");
        getActionBarLayoutBinding().setting.setVisibility(View.VISIBLE);
        getActionBarLayoutBinding().setting.setOnClickListener(v -> {
            updateStudent();
        });
        Student student = (Student) getIntent().getSerializableExtra("student");
        studentInfoBinding.etStudentBirth.setText(student.getStudentBirth());

        if (student.getStudentGender().equals("男")) {
            studentInfoBinding.female.setChecked(false);
            studentInfoBinding.male.setChecked(true);
        } else if (student.getStudentGender().equals("女")) {
            studentInfoBinding.female.setChecked(true);
            studentInfoBinding.male.setChecked(false);
        }

        studentInfoBinding.etStudentHometown.setText(student.getStudentHometown());
        studentInfoBinding.etStudentId.setText(student.getStudentId());
        studentInfoBinding.etStudentMajor.setText(student.getStudentMajor());
        studentInfoBinding.etStudentPhone.setText(student.getStudentPhone());
        studentInfoBinding.etStudentName.setText(student.getStudentName());
        studentInfoBinding.etStudentBirth.setFocusable(false);
        studentInfoBinding.etStudentBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }

    private void showDatePicker() {
        //时间选择器
        new TimePickerBuilder(StudentInfoActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                studentInfoBinding.etStudentBirth.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .build().show();
    }

    private void updateStudent() {
        Student student = getStudent();
        try {
            AppDatabase.getInstance().studentDao().updateStudent(student);
            toast("编辑成功");
            this.finish();
        } catch (Exception e) {
            toast("编辑失败");
        }
    }

    @NotNull
    private Student getStudent() {
        studentBirth = studentInfoBinding.etStudentBirth.getText().toString();
        int genderButtonId = studentInfoBinding.genderRG.getCheckedRadioButtonId();
        studentGender = genderButtonId == R.id.male ? "男" : "女";
        studentHometown = studentInfoBinding.etStudentHometown.getText().toString();
        studentId = studentInfoBinding.etStudentId.getText().toString();
        studentMajor = studentInfoBinding.etStudentMajor.getText().toString();
        studentPhone = studentInfoBinding.etStudentPhone.getText().toString();
        studentName = studentInfoBinding.etStudentName.getText().toString();
        Student student = new Student();
        student.setStudentName(studentName);
        student.setStudentBirth(studentBirth);
        student.setStudentHometown(studentHometown);
        student.setStudentMajor(studentMajor);
        student.setStudentPhone(studentPhone);
        student.setStudentGender(studentGender);
        student.setStudentId(studentId);
        return student;
    }

    public void delStudent(View v) {
        studentId = studentInfoBinding.etStudentId.getText().toString();
        AppDatabase.getInstance().studentDao().deleteStudentClassById(studentId);
        toast("删除成功");
        this.finish();
    }

    public String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
