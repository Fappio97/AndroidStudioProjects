package it.unical.demacs.myapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_welcome.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_USER = "arg_username"

/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var username: String? = null
    private var listener: OnWelcomeFragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_USER)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    companion object {
        const val TAG = "WelcomeFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic fun newInstance(param1: String) =
                WelcomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_USER, param1)
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTxt.text = username
        Log.d("DEBUG NAMETXT", nameTxt.text.toString())
        logoutIcn.setOnClickListener(this)
        logoutTxt.setOnClickListener(this)
        nextIcn.setOnClickListener(this)
        nextTxt.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnWelcomeFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnWelcomeFragmentListener")
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logoutIcn,
            R.id.logoutTxt -> {
                listener?.logout()
            }
            R.id.nextTxt,
            R.id.nextIcn -> {
                listener?.goToMainSection()
            }
        }
    }

    interface OnWelcomeFragmentListener {
        fun logout()
        fun goToMainSection()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}