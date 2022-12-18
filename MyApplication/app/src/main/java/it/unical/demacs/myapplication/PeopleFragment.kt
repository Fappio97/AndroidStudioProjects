package it.unical.demacs.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*


class PeopleFragment : Fragment() {

    private var myListener: OnPeopleFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnPeopleFragmentListener) {
            myListener = context
        } else {
            throw RuntimeException(context.getString(R.string.wrapper_exception, context.toString()))
        }
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = PeopleRecyclerAdapter(myListener!!)

        }
    }

    interface OnPeopleFragmentListener {
        fun onPersonListener(username: String)
    }

    companion object {
        const val PEOPLE_TAG = "people_tag"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = PeopleFragment()
    }
}