package it.unical.demacs.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_detail_person.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailPersonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class DetailPersonFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
/*
        val intent = Intent(activity, DetailPerson::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)*/
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DETAIL_FRAGMENT", "QUI")

        val user = arguments?.getString(ARG_PARAM1) ?: ""

        if(user != "") {
            Log.d("DETAIL_FRAGMENT", "DENTRO IF")
            val course = Login.instance.getDetail(user)

            Log.d("DEBUG", "${nomeCorso.text}")
            nomeCorso.text = course.username
            codiceCorso.text = course.country
            materialeCorso.text = course.details

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_detail_person, container, false)

        Log.d("DETAIL_FRAGMENT", "QUA")

        return view

    }

    companion object {
        var ARG_PARAM1 = "USER_KEY"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailPersonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) : DetailPersonFragment {
            val args = Bundle()
            Log.d("DETAILG_FRAGMENT", "$param1")

            args.putString(ARG_PARAM1, param1)
            Log.d("DETAILG_FRAGMENT", "${args.getString(ARG_PARAM1)}")

            return DetailPersonFragment ().apply {
                arguments = args
            }
        }

    }
}