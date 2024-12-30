package com.example.taskmanager.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.ui.db.dao.TaskDao
import com.example.taskmanager.ui.db.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "task_database"
        const val DATABASE_VERSION = 1

        const val TASK_TABLE = "task_table"

        private var INSTANCE: DBHandler? = null

        fun getInstance(context: Context): DBHandler {

            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    DBHandler::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return INSTANCE!!
        }
    }
}