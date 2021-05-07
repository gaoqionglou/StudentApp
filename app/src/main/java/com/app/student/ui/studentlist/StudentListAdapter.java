package com.app.student.ui.studentlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.student.R;
import com.app.student.databinding.StudentListItemBinding;
import com.app.student.model.Student;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    private List<Student> mStudents;
    private Context mContext;

    public StudentListAdapter(List<Student> students, Context mContext) {
        this.mStudents = students;
        this.mContext = mContext;
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
