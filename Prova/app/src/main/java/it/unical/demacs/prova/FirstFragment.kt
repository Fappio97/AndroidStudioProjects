package it.unical.demacs.prova

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_first.*
import java.lang.RuntimeException

const val editText = "EditTextFirstFragment"

class FirstFragment : Fragment() {

    private var myListener : OnFirstFragment ?= null

    interface OnFirstFragment {
        fun onSecondActivity(string: String)
        fun onPreferenceActivity()
        fun onTaskActivity()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("DEBUG", "STO SALVANDO UN'ISTANZA")
        outState.putString(editText, edit_text_first_fragment.text.toString())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnFirstFragment) {
            myListener = context
        } else {
            throw RuntimeException("Error")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState != null) {
            edit_text_first_fragment.setText(savedInstanceState.getString(editText))
            Log.d("DEBUG", "RIPRISTINO FRAGMENT")
        }

        button_first_fragment.setOnClickListener {
            myListener?.onSecondActivity(edit_text_first_fragment.text.toString())
        }

        preferences.setOnClickListener {
            myListener?.onPreferenceActivity()
        }

        task.setOnClickListener {
            myListener?.onTaskActivity()
        }
    }

    companion object {
        const val TAG = "FirstFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = FirstFragment()
    }
}