package com.example.taskmanager.ui.mvp.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.databinding.ActivityMainBinding
import com.example.taskmanager.databinding.CutomDialogBinding
import com.example.taskmanager.ui.adapter.RecyclerTaskAdapter
import com.example.taskmanager.ui.db.model.TaskEntity
import com.example.taskmanager.ui.ext.OnBindData

class ViewMainActivity(
    contextInstance: Context,
) : FrameLayout(contextInstance) {


    private lateinit var adapter: RecyclerTaskAdapter

    val binding = ActivityMainBinding.inflate(LayoutInflater.from(context))


    fun showTask(tasks: List<TaskEntity>) {

        val data = arrayListOf<TaskEntity>()
        tasks.forEach {
            data.add(it)
        }
        adapter.dataUpdate(data)
    }


    fun setData(onBindData: OnBindData) {


        onBindData.requestData(false)

        binding.RBTrue.setOnClickListener {
            onBindData.requestData(true)
        }

        binding.RBFalse.setOnClickListener {
            onBindData.requestData(false)
        }
    }

    fun showDialog(onBindData: OnBindData) {

        binding.fab.setOnClickListener {
            val view = CutomDialogBinding.inflate(LayoutInflater.from(context))

            val dialog = Dialog(context)
            dialog.setContentView(view.root)
            dialog.setCancelable(false)
            //خط بعد به این دلیل اومده: هر دیالوگی برای خودش یه
            // بک‌گراند داره این خط میاد میگه تو بک‌گراندت هیچی باشه یعنی ترنس‌پرنت باشه
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            view.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
            view.btnSave.setOnClickListener {

                val text = view.edtTask.text.toString()

                if (text.isEmpty())
                    Toast.makeText(context, "تسک خالی برنمی‌داریم", Toast.LENGTH_SHORT).show()
                else {
                    onBindData.saveData(TaskEntity(title = text, state = false))
                    Toast.makeText(context, "ذخیره شد", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }
        }
    }


    fun initRecycler(tasks: ArrayList<TaskEntity>, onBindData: OnBindData) {

        adapter = RecyclerTaskAdapter(onBindData, tasks)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                context, RecyclerView.VERTICAL, false
            )
        binding.recyclerView.adapter = adapter
    }
}