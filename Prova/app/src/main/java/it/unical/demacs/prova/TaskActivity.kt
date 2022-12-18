package it.unical.demacs.prova

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TaskActivity : AppCompatActivity(), TaskFragment.OnTaskFragmentListener {

    companion object {

        lateinit var adapterTask: TaskAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        adapterTask = TaskAdapter(this)

        val secondFragment = TaskFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_activity, secondFragment, TaskFragment.TAG).commit()
    }

    override fun onStartPressed() {
        var taskAsync: TaskAsyncTask = TaskAsyncTask(this)
        taskAsync.execute()
    }

    override fun onStopPressed() {
        var taskAsync: TaskAsyncTask = TaskAsyncTask(this)
        taskAsync.cancel(true)
    }

}