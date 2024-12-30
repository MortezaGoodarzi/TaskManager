package com.example.taskmanager.ui.mvp.model

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.taskmanager.ui.db.DBHandler
import com.example.taskmanager.ui.db.model.TaskEntity
import com.example.taskmanager.ui.ext.OnBindData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ModelMainActivity(
    private val activity: AppCompatActivity
) {

    private val db = DBHandler.getInstance(activity)


    fun setData(taskEntity: TaskEntity) {

        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().insertTask(taskEntity)
            }
        }
    }


    fun getData(state: Boolean, onBindData: OnBindData) {

        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val tasks = db.taskDao().getTasksByColumn(state)

                withContext(Dispatchers.Main) {
                    tasks.collect {
                        onBindData.getData(it)
                    }
                }
            }
        }
    }


    fun editData(task: TaskEntity) {

        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().updateTasks(task)
            }
        }
    }


    fun deleteData(task: TaskEntity) {

        activity.lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                db.taskDao().deleteTasks(task)
            }
        }
    }


    fun closeDatabase() {

        db.close()
    }
}