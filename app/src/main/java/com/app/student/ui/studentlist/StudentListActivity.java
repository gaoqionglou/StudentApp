package com.app.student.ui.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.student.base.BaseActivity;
import com.app.student.common.Constants;
import com.app.student.databinding.ActionBarLayoutBinding;
import com.app.student.databinding.ActivityStudentListBinding;
import com.app.student.model.Student;
import com.app.student.ui.add.AddStudentActivity;

import java.util.List;

public class StudentListActivity extends BaseActivity {
    private ActivityStudentListBinding studentListBinding;
    private StudentListViewModel viewModel;
    private StudentListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        studentListBinding = ActivityStudentListBinding.inflate(LayoutInflater.from(this));
        setContentView(studentListBinding.getRoot());
        viewModel = new ViewModelProvider(this).get(StudentListViewModel.class);
        setCustomActionBar();
        mAdapter = new StudentListAdapter(null, this);
        studentListBinding.rvStudentList.setLayoutManager(new LinearLayoutManager(this));
        studentListBinding.rvStudentList.setAdapter(mAdapter);
        viewModel.getStudentList().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> follows) {
                studentListBinding.empty.setVisibility((follows == null || follows.isEmpty()) ? View.VISIBLE : View.GONE);
                mAdapter.setData(follows);
            }
        });
        studentListBinding.add.setOnClickListener(v -> {
            Intent intent = new Intent(StudentListActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.queryAllStudent();
    }

    @Override
    public void setCustomActionBar() {
        ActionBarLayoutBinding actionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.e(Constants.TAG, "actionBar null set style!");
            return;
        }
        actionBarViewBinding.arrowBack.setVisibility(View.VISIBLE);
        actionBarViewBinding.arrowBack.setOnClickListener(v -> {
            this.finish();
        });
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBarViewBinding.title.setText("学生信息");
    }
}
