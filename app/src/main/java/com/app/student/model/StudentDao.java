package com.app.student.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student std);

    @Query("SELECT * FROM Student where student_id = :stdId")
    List<Student> getStudentClassesByDay(String stdId);

    @Update
    void updateStudent(Student std);

    @Query("SELECT * FROM Student")
    List<Student> getStudentClass();

    @Query("DELETE FROM Student")
    int deleteAll();

    @Query("DELETE FROM Student where student_id=:stdId")
    int deleteStudentClassById(String stdId);
}
