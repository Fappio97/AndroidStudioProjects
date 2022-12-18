package it.unical.demacs.prova

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.AsyncTask
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_task.*


class TaskAdapter(
    context: Context
    ) :
    ArrayAdapter<Applicazione>(context, 0, ArrayList<Applicazione>()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var textView = convertView as? TextView
        if(textView == null) {
            textView = TextView(context)
            textView.gravity = Gravity.CENTER
            textView.setLines(1)
        }
        var item: Applicazione = getItem(position)!!
        textView.text = item!!.nome
        textView.setCompoundDrawablesWithIntrinsicBounds(null, item.icona, null, null)
        return textView
    }

}
/*
class TaskAsyncTask(var mainActivity: TaskActivity,
                    private var packageManager: PackageManager = mainActivity.packageManager
)
    : AsyncTask<Void, Void, List<Applicazione>>() {


    override fun doInBackground(vararg p0: Void?): List<Applicazione> {
        Log.d("DEBUG", "QUI")
        val apps: List<ResolveInfo> = Applicazione.getAllInstalledApps(packageManager)
        val result: MutableList<Applicazione> = ArrayList()
        for (resolveInfo in apps) {
            Log.d("DEBUG", "FOR")
            result.add(
                Applicazione(
                    resolveInfo,
                    packageManager
                )
            )
        }
        return result
    }



    override fun onPreExecute() {
        TaskActivity.adapterTask.clear()
        mainActivity.start.isEnabled = false
    }

    override fun onPostExecute(result: List<Applicazione>?) {
        super.onPostExecute(result)
        mainActivity.start.isEnabled = true

        if (result != null) {
            for (applicazione in result) {
                Log.d("DEBUG", "AGGIUNTO + $applicazione")
                TaskActivity.adapterTask.add(applicazione)
            }
        }
        Log.d("DEBUG", "${TaskActivity.adapterTask}")
    }



}*/

class TaskAsyncTask(var mainActivity: TaskActivity,
                    private var packageManager: PackageManager = mainActivity.packageManager
)
    : AsyncTask<Void, Applicazione, Void>() {


    override fun doInBackground(vararg p0: Void?): Void? {
        val apps: List<ResolveInfo> = Applicazione.getAllInstalledApps(packageManager)
        for (resolveInfo in apps) {
            publishProgress(Applicazione(resolveInfo, packageManager))
        }
        return null
    }

    override protected fun onProgressUpdate(vararg values: Applicazione?) {
        for (application in values) {
            TaskActivity.adapterTask.add(application)
        }
    }



    override fun onPreExecute() {
        TaskActivity.adapterTask.clear()
        mainActivity.start.isEnabled = false
    }

    override fun onCancelled() {
        mainActivity.start.isEnabled = true
        mainActivity.stop.isEnabled = false
    }

}

