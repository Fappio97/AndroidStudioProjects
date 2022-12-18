package it.unical.demacs.siieco.ui.authentication

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthenticationFragmentViewModel: ViewModel() {

    private var _email: String = ""
    var email: String
        get() = _email
        set(value) {
            _email = value
        }

    fun validEmail(): Boolean = email.isValidEmail()

    private fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

}