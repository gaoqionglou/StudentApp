package com.app.student.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.student.MyApp;
import com.app.student.model.Student;
import com.app.student.model.StudentDao;
import com.app.student.model.User;
import com.app.student.model.UserDao;


//app数据库初始化
@Database(entities = {Student.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "student_app.db";

    private static AppDatabase mAppDatabase;

    public static AppDatabase getInstance() {
        if (mAppDatabase == null) {
            synchronized (AppDatabase.class) {
                if (mAppDatabase == null) {
                    mAppDatabase = Room.databaseBuilder(MyApp.getContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return mAppDatabase;
    }

    public abstract StudentDao studentDao();

    public abstract UserDao userDao();
}
