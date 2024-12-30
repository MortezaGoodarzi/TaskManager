package com.example.taskmanager.ui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanager.ui.db.DBHandler
import com.example.taskmanager.ui.db.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {


    @Insert
    fun insertTask(vararg task: TaskEntity)

    @get:Query("SELECT * FROM ${DBHandler.TASK_TABLE}")
    val getTasks: Flow<List<TaskEntity>>

    @Query("SELECT * FROM ${DBHandler.TASK_TABLE} WHERE state = :type")
    fun getTasksByColumn(type: Boolean): Flow<List<TaskEntity>>

    @Update
    fun updateTasks(vararg tasks: TaskEntity): Int

    @Delete
    fun deleteTasks(vararg tasks: TaskEntity): Int

    @Query("DELETE FROM ${DBHandler.TASK_TABLE}")
    fun deleteAllTasks()
}