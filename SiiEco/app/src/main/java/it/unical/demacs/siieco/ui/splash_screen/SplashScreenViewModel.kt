package it.unical.demacs.siieco.ui.splash_screen

import androidx.lifecycle.ViewModel
import it.unical.demacs.siieco.R
import it.unical.demacs.siieco.common.Functions
import it.unical.demacs.siieco.data.remote.AuthRepository

class SplashScreenViewModel: ViewModel() {

    fun checkCurrentUser() = AuthRepository.checkCurrentUser()

    fun getSloganRandom(array: Array<String>): String {
        val randomIndex = Functions.random(array.size)
        return array[randomIndex]
    }

}