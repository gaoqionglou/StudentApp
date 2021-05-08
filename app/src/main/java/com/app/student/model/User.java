package com.app.student.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "User")
public class User implements Serializable {


    @NonNull
    @PrimaryKey()
    @ColumnInfo(name = "student_id")
    private String studentId;
    @ColumnInfo(name = "studentPsw")
    private String studentPsw;

    @NonNull
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(@NonNull String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPsw() {
        return studentPsw;
    }

    public void setStudentPsw(String studentPsw) {
        this.studentPsw = studentPsw;
    }

    @Override
    public String toString() {
        return "User{" +
                "studentId='" + studentId + '\'' +
                ", studentPsw='" + studentPsw + '\'' +
                '}';
    }
}