package com.app.student.util;

import com.app.student.MyApp;
import com.app.student.R;
import com.app.student.database.AppDatabase;
import com.app.student.model.Student;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataCreator {

    //生成课程表数据
    public static void makeClassData() {
        try {

            AppDatabase.getInstance().studentDao().deleteAll();
            String json = FileUtil.getRawFile(R.raw.data);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String student_id = obj.getString("student_id");
                String studentName = obj.getString("studentName");
                String studentGender = obj.getString("studentGender");
                String studentBirth = obj.getString("studentBirth");
                String studentHometown = obj.getString("studentHometown");

                String studentMajor = obj.getString("studentMajor");
                String studentPhone = obj.getString("studentPhone");

                Student std = new Student();
                std.setStudentId(UUIDCreator.uuid());
                std.setStudentGender(studentGender);
                std.setStudentName(studentName);
                std.setStudentBirth(studentBirth);
                std.setStudentHometown(studentHometown);
                std.setStudentMajor(studentMajor);
                std.setStudentPhone(studentPhone);
                AppDatabase.getInstance().studentDao().insertStudent(std);
            }
            SharedPreferencesUtils.saveBoolean(MyApp.getMyApplication().getApplicationContext(), "isDataInit", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
