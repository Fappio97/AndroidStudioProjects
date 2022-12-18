package it.unical.demacs.siieco.data.remote

import android.app.Activity
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import it.unical.demacs.siieco.domain.model.User

object AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    fun getCurrentUser(): User? {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val id = user.uid
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl
            return User(id, name, email!!, photoUrl)
        }
        return null
    }

    fun checkCurrentUser() = auth.currentUser

    fun logout(activity: Activity) {
        // set the new task and clear flags
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("272639697769-9p6acjtva9g125r83gs7ebe1t16bn0ha.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(activity, gso)
        googleSignInClient.signOut()
        googleSignInClient.signOut()

        Firebase.auth.signOut()
        LoginManager.getInstance().logOut()
    }

    fun isSignInWithEmailLink(link: String): Boolean = auth.isSignInWithEmailLink(link)

    fun sendSignInLinkToEmail(email: String, actionCodeSettings: ActionCodeSettings) = auth.sendSignInLinkToEmail(email, actionCodeSettings)

    fun signInWithEmailLink(email: String, intentData: String) = auth.signInWithEmailLink(email, intentData)

    fun signInWithCredential(credential: AuthCredential) = auth.signInWithCredential(credential)

    fun linkWithCredential(credential: AuthCredential) = auth.currentUser!!.linkWithCredential(credential)

    fun useAppLanguage() = auth.useAppLanguage()

}