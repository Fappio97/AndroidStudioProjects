package it.unical.demacs.siieco.common

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import it.unical.demacs.siieco.data.remote.FirestoreRepository
import java.util.*

object Functions {

    //hide soft keyboard
    fun hideKeyboard(activity: Activity) {
        var imm: InputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        if (null != activity.currentFocus)
            imm.hideSoftInputFromWindow(
                activity.currentFocus!!
                    .applicationWindowToken, 0)
    }

    fun random(size: Int) = Random().nextInt(size)

    fun createUser(userId: String) {
        val userDocument = hashMapOf(
            "type" to "user"
        )

        addUserDb(userDocument, userId)
    }

    private fun addUserDb(userDocument: HashMap<String, String>, userId: String) = FirestoreRepository.addUser(userDocument, userId)


}