package com.example.taskmanager.ui.mvp.presenter

import com.example.taskmanager.ui.db.model.TaskEntity
import com.example.taskmanager.ui.ext.BaseLifecycle
import com.example.taskmanager.ui.ext.OnBindData
import com.example.taskmanager.ui.mvp.model.ModelMainActivity
import com.example.taskmanager.ui.mvp.view.ViewMainActivity

class PresenterMainActivity(
    private val view: ViewMainActivity,
    private val model: ModelMainActivity
) : BaseLifecycle {

    override fun onCreate() {

        setNewTask() 
        initRecycler()
        dataHandler()
    }


    private fun setNewTask() {

        view.showDialog(
            object : OnBindData {
                override fun saveData(taskEntity: TaskEntity) {
                    model.setData(taskEntity)
                }
            }
        )
    }


    private fun initRecycler() {

        view.initRecycler(
            arrayListOf(),
            object : OnBindData {
                override fun editData(taskEntity: TaskEntity) {
                    model.editData(taskEntity)
                }

                override fun deleteData(taskEntity: TaskEntity) {
                    model.deleteData(taskEntity)
                }
            }
        )
    }

    private fun dataHandler() {
        view.setData(object : OnBindData {
            override fun requestData(state: Boolean) {
                model.getData(state,
                    object : OnBindData {
                        override fun getData(taskEntity: List<TaskEntity>) {
                            view.showTask(taskEntity)
                        }

                    }
                )
            }
        })

    }


    override fun onDestroy() {

        model.closeDatabase()
    }

}