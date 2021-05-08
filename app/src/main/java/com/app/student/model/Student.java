package com.app.student.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//数据库 学生表
@Entity(tableName = "Student")
public class Student implements Serializable {

    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "student_id")
    private String studentId;

    @ColumnInfo(name = "studentName")
    private String studentName;

    @ColumnInfo(name = "studentGender")
    private String studentGender;

    @ColumnInfo(name = "studentBirth")
    private String studentBirth;

    @ColumnInfo(name = "studentHometown")
    private String studentHometown;

    @ColumnInfo(name = "studentMajor")
    private String studentMajor;

    @ColumnInfo(name = "studentPhone")
    private String studentPhone;

    @NonNull
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(@NonNull String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentBirth() {
        return studentBirth;
    }

    public void setStudentBirth(String studentBirth) {
        this.studentBirth = studentBirth;
    }

    public String getStudentHometown() {
        return studentHometown;
    }

    public void setStudentHometown(String studentHometown) {
        this.studentHometown = studentHometown;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
}
