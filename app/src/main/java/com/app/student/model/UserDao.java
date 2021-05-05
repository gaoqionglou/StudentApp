package com.app.student.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User where student_id = :stdId")
    List<User> getUserById(String stdId);

    @Query("SELECT * FROM User where student_id = :stdId AND studentPsw = :psw")
    List<User> getUserByIdAndPsw(String stdId, String psw);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("DELETE FROM User")
    int deleteAll();

    @Query("DELETE FROM User where student_id=:stdId")
    int deleteUserById(String stdId);

}
