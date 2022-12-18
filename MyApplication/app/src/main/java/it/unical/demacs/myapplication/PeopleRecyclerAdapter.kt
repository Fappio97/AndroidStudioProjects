package it.unical.demacs.myapplication

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class PeopleRecyclerAdapter(
    private val myListener : PeopleFragment.OnPeopleFragmentListener
) : RecyclerView.Adapter<PeopleRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var username : TextView
        var detail : TextView

        init {
            username = view.findViewById(R.id.card_username)
            detail = view.findViewById(R.id.card_detail)

            view.setOnClickListener {
                Log.d("PeopleRecycler", "${username.text.toString()}")
                myListener.onPersonListener(username.text.toString())

            }

        }

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v : View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_detail_card, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.username.text = Login.instance.getUserElement(i).username
        viewHolder.detail.text = Login.instance.getUserElement(i).country
    }

    override fun getItemCount(): Int = Login.instance.getSize()

    companion object {
        const val USERNAME = "INTENT_USERNAME"
    }

}