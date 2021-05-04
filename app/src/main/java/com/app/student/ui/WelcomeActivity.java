package com.app.student.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.student.MyApp;
import com.app.student.database.AppDatabase;
import com.app.student.databinding.ActivityWelcomeBinding;
import com.app.student.util.DataCreator;
import com.app.student.util.SharedPreferencesUtils;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends AppCompatActivity {
    ActivityWelcomeBinding activityWelcomeBinding;
    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWelcomeBinding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this));
        setContentView(activityWelcomeBinding.getRoot());
        PermissionUtils.permission(PermissionConstants.STORAGE).request();
        if (SharedPreferencesUtils.getBoolean(MyApp.getMyApplication().getApplicationContext(), "isDataInit", false)) {
            //如果已经做了初始化，那么直接进入课表页面
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        //没做过数据初始化的，进行数据初始化并加入数据库
        DataCreator.makeClassData();
        mHandler.sendEmptyMessageDelayed(1, 1000);
        //动态权限申请
        PermissionUtils.permission(PermissionConstants.STORAGE).request();


    }


    static class MyHandler extends Handler {
        WeakReference<Activity> mActivityReference;

        MyHandler(Activity activity) {
            mActivityReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mActivityReference.get();
            if (activity != null) {
                Log.i("app", AppDatabase.getInstance().studentDao().getStudentClass().toString());
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }
}
