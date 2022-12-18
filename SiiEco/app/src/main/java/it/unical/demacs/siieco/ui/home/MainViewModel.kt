package it.unical.demacs.siieco.ui.home

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import it.unical.demacs.siieco.data.remote.AuthRepository

class MainViewModel: ViewModel() {

    fun getCurrentUser() = AuthRepository.getCurrentUser()

    fun logout(activity: Activity) = AuthRepository.logout(activity)

    fun linkWithCredential(credential: AuthCredential) = AuthRepository.linkWithCredential(credential)
}