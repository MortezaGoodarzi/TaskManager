package com.example.taskmanager.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.RecyclerItemBinding
import com.example.taskmanager.ui.db.model.TaskEntity
import com.example.taskmanager.ui.ext.OnBindData

class RecyclerTaskAdapter(
    private val onBindData: OnBindData,
    private val tasks: ArrayList<TaskEntity>
) : RecyclerView.Adapter<RecyclerTaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        return TaskViewHolder(
            RecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        holder.setData(tasks[position])
    }


    override fun getItemCount(): Int = tasks.size


    inner class TaskViewHolder(
        private val binding: RecyclerItemBinding
    ) : ViewHolder(binding.root) {
        fun setData(data: TaskEntity) {
            binding.txtTitle.text = data.title
            binding.checkBox.isChecked = data.state

            binding.checkBox.setOnClickListener {
                if (binding.checkBox.isChecked) {
                    onBindData.editData(TaskEntity(data.id, data.title, true))
                } else {
                    onBindData.editData(TaskEntity(data.id, data.title, false))
                }
            }

            binding.imageView.setOnClickListener {
                onBindData.deleteData(data)

            }
        }
    }

    fun dataUpdate(newList: ArrayList<TaskEntity>) {

        val diffCallBack = RecyclerDiffUtils(tasks, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        tasks.clear()
        tasks.addAll(newList)

        diffResult.dispatchUpdatesTo(this)
    }

}