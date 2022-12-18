package it.unical.demacs.siieco

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import it.unical.demacs.siieco.common.Constants
import it.unical.demacs.siieco.common.Functions
import it.unical.demacs.siieco.data.local.PreferenceRepository
import it.unical.demacs.siieco.domain.model.User
import it.unical.demacs.siieco.ui.authentication.AuthenticationFragment
import it.unical.demacs.siieco.ui.authentication.AuthenticationViewModel
import it.unical.demacs.siieco.ui.authentication.EmailFragment


private const val KEY_CURRENT_FRAGMENT = "CURRENT_FRAGMENT"

class AuthenticationActivity : AppCompatActivity(),
    AuthenticationFragment.OnAuthenticationFragmentListener,
    EmailFragment.OnEmailFragmentListener {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var preferenceRepository: PreferenceRepository
    private val authenticationViewModel: AuthenticationViewModel by viewModels()

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_FRAGMENT, authenticationViewModel.currentFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        // shared preference locali
        preferenceRepository = PreferenceRepository(this)

        if (savedInstanceState != null) {
            val savedCurrentFragment = savedInstanceState.getInt(KEY_CURRENT_FRAGMENT)
            authenticationViewModel.currentFragment = savedCurrentFragment
        } else {
            changeFragment()
        }

        requestGoogle()

    }

    // called when user verify his email
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.data != null) {
            val intentData = intent.data.toString()

            //Check if the emailLink is valid
            if (authenticationViewModel.isSignInWithEmailLink(intentData)) {
                authenticationViewModel.verifySignInLink(intentData, this, preferenceRepository)
            } else {
                Toast.makeText(this, getString(R.string.invalid_link_code), Toast.LENGTH_SHORT).show()
            }
        }
    }


    // google login
    private fun requestGoogle() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("272639697769-9p6acjtva9g125r83gs7ebe1t16bn0ha.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onGooglePressed() {
        if(!authenticationViewModel.isNetworkAvailable(this)) {
            Toast.makeText(this, resources.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        } else {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // se è google
        if (requestCode == Constants.RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
//                    Log.d("LOGIN-GOOGLE", "firebaseAuthWithGoogle:" + account.id)
                    //    firebaseAuthWithGoogle(account.idToken!!)
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
//                    Log.d("CREDENTIAL", credential.toString())

                    linkCredential(credential)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
//                    Log.w("LOGIN-GOOGLE", "Google sign in failed", e)
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }
        } else{
            Log.d("LOGIN-FACEBOOK", "ELSE")
            //If not request code is RC_SIGN_IN it must be facebook
            val callbackManager = CallbackManager.Factory.create()
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun linkCredential(credential: AuthCredential) {
        authenticationViewModel.signInWithCredential(credential)
            .addOnCompleteListener(this) { task->
                if (task.isSuccessful) {
                    Log.d("LOGIN", "SUCCESS")
//                    Log.d("LOGIN-GLOBALE", "linkWithCredential:success")
                    if(task.result.additionalUserInfo?.isNewUser == true) {
                        Log.d("LOGIN-GOOGLE_USER", "NEW USER")

                        val user: User = authenticationViewModel.getCurrentUser()!!

                        Functions.createUser(user.uid)

                    } else {
                        Log.d("LOGIN-EMAIL_USER", "NOT NEW USER")
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
 //                   Log.w("LOGIN-GLOBALE", "linkWithCredential:failure", task.exception)
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseNetworkException) {
                        Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show()
                    } catch(e: FirebaseException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
    }
    // fine login google

    // utility

    private fun changeFragment() {
        when(authenticationViewModel.currentFragment) {
            0 -> {
                val fragment: AuthenticationFragment = AuthenticationFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_authentication, fragment, AuthenticationFragment.TAG)
                    .commit()
            }
            1 -> {
                val mail:String = preferenceRepository.getEmail()

                val fragment: EmailFragment = EmailFragment.newInstance(mail)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_authentication, fragment, EmailFragment.TAG)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if(authenticationViewModel.currentFragment == 1) {
            authenticationViewModel.changeCurrentFragmentInt()
            changeFragment()
        } else
            super.onBackPressed()
    }

    // fine utility

    // login email

    override fun onConnectionPressed(email: String) {
        authenticationViewModel.hideKeyboard(this)
        if(!authenticationViewModel.isNetworkAvailable(this)) {
            Toast.makeText(this, resources.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
        } else {
            authenticationViewModel.connectionEmail(email)

            //salvo la mail che poi serve per la login
            preferenceRepository.putEmail(email)

            authenticationViewModel.changeCurrentFragmentInt()
            changeFragment()
        }
    }



    // fine login email

    // login facebook
    override fun onFacebookPressed(token: AccessToken) {
        handleFacebookAccessToken(token)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(AuthenticationFragment.TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)

        authenticationViewModel.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(AuthenticationFragment.TAG, "signInWithCredential:success")

                    if(task.result.additionalUserInfo?.isNewUser == true) {
                        Log.d("LOGIN-FACEBOOK_USER", "NEW USER")

                        val user: User = authenticationViewModel.getCurrentUser()!!

                        Functions.createUser(user.uid)

                    } else {
                        Log.d("LOGIN-EMAIL_USER", "NOT NEW USER")
                    }

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(AuthenticationFragment.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    // fine login facebook

    // EMAIL FRAGMENT
    override fun onResendEmail() {
        authenticationViewModel.connectionEmail(preferenceRepository.getEmail())
    }

    override fun onOpenEmailClient() {
        clientEmail()
    }

    private fun clientEmail() {
        try {
            //take all email client
/*            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            this.startActivity(Intent.createChooser(intent, getString(R.string.choose_email_client)))*/

            val emailChooserIntent = authenticationViewModel.emailAppIntent(this)

            if (emailChooserIntent != null)
                startActivity(authenticationViewModel.emailAppIntent(this))

        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.there_is_no_email_client_installed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    //----------------------- CHANGE LINK PRIVACY POLICY
    override fun onPrivacyPolicyPressed() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Impossible open privacy policy", Toast.LENGTH_SHORT).show()
        }
    }

}