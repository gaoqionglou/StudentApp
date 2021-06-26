package com.app.student.ui.studentlist;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.student.R;
import com.app.student.databinding.StudentListItemBinding;
import com.app.student.model.Student;
import com.app.student.util.ToastUtil;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<Student> mStudents;
    private Context mContext;
    private StudentListViewModel viewModel;

    public StudentListAdapter(List<Student> students, Context mContext, StudentListViewModel viewModel) {
        this.mStudents = students;
        this.mContext = mContext;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student std = mStudents.get(position);
        holder.viewItemBinding.studentNo.setText("学号: " + std.getStudentId());
        holder.viewItemBinding.studentGender.setText("性别: " + std.getStudentGender());
        holder.viewItemBinding.studentName.setText("姓名: " + std.getStudentName());
        holder.viewItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StudentInfoActivity.class);
                intent.putExtra("student", std);
                mContext.startActivity(intent);
            }
        });
        holder.viewItemBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("[按学号删除学生信息]-确认要删除学生吗?")
                        .setPositiveButton("确定", (dialog, which) -> {
                            deleteItem(std, view, position);
                            ToastUtil.toast("[按学号删除学生信息]-成功");
                            dialog.dismiss();
                        })
                        .setNegativeButton("取消", ((dialog, which) -> {
                            dialog.dismiss();
                        }))
                        .show();

                return true;
            }
        });
    }

    private void deleteItem(Student std, View itemView, int position) {
        //数据库删除
        viewModel.deleteStudentById(std.getStudentId());
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 1, 0);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeData(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
    }

    //  删除数据
    public void removeData(int position) {
        mStudents.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mStudents == null ? 0 : mStudents.size();
    }

    public void setData(List<Student> data) {
        this.mStudents = data;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        StudentListItemBinding viewItemBinding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewItemBinding = StudentListItemBinding.bind(itemView);
        }
    }
}
