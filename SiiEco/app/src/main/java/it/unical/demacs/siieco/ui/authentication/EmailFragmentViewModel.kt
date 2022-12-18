package it.unical.demacs.siieco.ui.authentication

import android.text.SpannedString
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.ViewModel

class EmailFragmentViewModel: ViewModel() {

    private var _email: String = ""
    var email: String
        get() = _email
        set(value) {
            _email = value
        }

    private var _resendEmail: Boolean = true
    var resendEmail: Boolean
        get() = _resendEmail
        set(value) {
            _resendEmail = value
        }

    // email dell'utente text bold
    fun boldEmail(): SpannedString =
        buildSpannedString {
            bold { append(" $email") }
        }
}