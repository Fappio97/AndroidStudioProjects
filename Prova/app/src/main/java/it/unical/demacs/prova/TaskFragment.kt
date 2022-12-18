package it.unical.demacs.prova

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_task.*


/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {

    private var myListener: OnTaskFragmentListener ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    interface OnTaskFragmentListener {
        fun onStartPressed()
        fun onStopPressed()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnTaskFragmentListener)
            myListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        grid.adapter = TaskActivity.adapterTask

        start.setOnClickListener {
            myListener!!.onStartPressed()
        }

        stop.setOnClickListener {
            myListener!!.onStopPressed()
        }
    }

    companion object {
        const val TAG = "FragmentTask"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = TaskFragment()
    }
}

