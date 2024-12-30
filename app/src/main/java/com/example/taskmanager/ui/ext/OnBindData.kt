package com.example.taskmanager.ui.ext

import com.example.taskmanager.ui.db.model.TaskEntity

interface OnBindData {

    fun saveData(taskEntity: TaskEntity) {}

    fun editData(taskEntity: TaskEntity) {}

    fun getData(taskEntity: List<TaskEntity>) {}

    fun deleteData(taskEntity: TaskEntity) {}

    fun requestData(state: Boolean) {}

    fun requestShowTask(state: Boolean, tasks: List<TaskEntity>) {}
}