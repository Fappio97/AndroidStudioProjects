package it.unical.demacs.siieco.ui.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.LabeledIntent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.actionCodeSettings
import it.unical.demacs.siieco.MainActivity
import it.unical.demacs.siieco.R
import it.unical.demacs.siieco.common.Functions
import it.unical.demacs.siieco.data.local.PreferenceRepository
import it.unical.demacs.siieco.data.remote.AuthRepository
import it.unical.demacs.siieco.domain.model.User

class AuthenticationViewModel: ViewModel() {

    // quando metto il fragment setto il fragment corrente a zero
    // perché ho un'altro fragment per la mail e mi serve nel metodo back
    // per mostrare sempre il fragment 0
    private var _currentFragmnet: Int = 0
    var currentFragment: Int
        get() = _currentFragmnet
        set(value) {
            _currentFragmnet = value
        }

    fun changeCurrentFragmentInt() {
        currentFragment = if(currentFragment == 0)  1 else 0
    }

    fun emailAppIntent(activity: Activity): Intent? {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))

        val packageManager = activity.packageManager

        val activitiesHandlingEmails = packageManager.queryIntentActivities(emailIntent, 0)

        if (activitiesHandlingEmails.isNotEmpty()) {
            // use the first email package to create the chooserIntent
            val firstEmailPackageName = activitiesHandlingEmails.first().activityInfo.packageName
            val firstEmailInboxIntent = packageManager.getLaunchIntentForPackage(firstEmailPackageName)
            val emailAppChooserIntent = Intent.createChooser(firstEmailInboxIntent, activity.getString(R.string.choose_email_client))

            // created UI for other email packages and add them to the chooser
            val emailInboxIntents = mutableListOf<LabeledIntent>()
            for (i in 1 until activitiesHandlingEmails.size) {
                val activityHandlingEmail = activitiesHandlingEmails[i]
                val packageName = activityHandlingEmail.activityInfo.packageName
                val intent = packageManager.getLaunchIntentForPackage(packageName)
                emailInboxIntents.add(
                    LabeledIntent(
                        intent,
                        packageName,
                        activityHandlingEmail.loadLabel(packageManager),
                        activityHandlingEmail.icon
                    )
                )
            }
            val extraEmailInboxIntents = emailInboxIntents.toTypedArray()
            return emailAppChooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraEmailInboxIntents)
        } else {
            return null
        }
    }

    // check connessione
    fun isNetworkAvailable(activity: Activity): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }


    // login email
    fun isSignInWithEmailLink(intentData: String) = AuthRepository.isSignInWithEmailLink(intentData)

    private fun sendSignInLinkToEmail(email: String, actionCodeSettings: ActionCodeSettings) = AuthRepository.sendSignInLinkToEmail(email, actionCodeSettings)

    fun connectionEmail(email: String) {
        val actionCodeSettings = actionCodeSettings {
            // URL you want to redirect back to. The domain (www.example.com) for this
            // URL must be whitelisted in the Firebase Console.
            url = "https://siieco.page.link/jTpt"
            // This must be true
            handleCodeInApp = true
            setIOSBundleId("com.example.ios")
            setAndroidPackageName(
                "it.unical.demacs.siieco",
                true, /* installIfNotAvailable */
                "12" /* minimumVersion */)
        }

        AuthRepository.useAppLanguage()

        sendSignInLinkToEmail(email, actionCodeSettings)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LOGIN-EMAIL", "Email sent.")
                }
            }
    }

    fun verifySignInLink(intentData: String, activity: Activity, preferenceRepository: PreferenceRepository) {

        // Retrieve this from wherever you stored it
        val email = preferenceRepository.getEmail()

        // The client SDK will parse the code from the link for you.
        signInWithEmailLink(email, intentData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("LOGIN", "SUCCESS")
                    // You can access the new user via result.getUser()
                    // Additional user info profile *not* available via:
                    // result.getAdditionalUserInfo().getProfile() == null
                    // You can check if the user is new or existing:
                    // result.getAdditionalUserInfo().isNewUser()
                    val user: User = getCurrentUser()!!

                    if(task.result.additionalUserInfo?.isNewUser == true) {
                        Log.d("LOGIN-EMAIL_USER", "NEW USER")
                        Functions.createUser(user.uid)
                    } else {
                        Log.d("LOGIN-EMAIL_USER", "NOT NEW USER")
                    }

                    activity.startActivity(Intent(activity, MainActivity::class.java))
                    activity.finish()
                } else {
                    Toast.makeText(activity, activity.getString(R.string.invalid_link_code), Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun getCurrentUser() = AuthRepository.getCurrentUser()


    private fun signInWithEmailLink(email: String, intentData: String) = AuthRepository.signInWithEmailLink(email, intentData)
    // fine login email


    // login facebook

    fun signInWithCredential(credential: AuthCredential) = AuthRepository.signInWithCredential(credential)

    // fine login facebook

    fun hideKeyboard(activity: Activity) = Functions.hideKeyboard(activity)


}
