package com.app.student.util;

import android.widget.Toast;

import com.app.student.MyApp;

public class ToastUtil {
    public static void toast(String text) {
        Toast.makeText(MyApp.getMyApplication().getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
