package it.unical.demacs.siieco.ui.authentication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import it.unical.demacs.siieco.R
import it.unical.demacs.siieco.ui.authentication.AuthenticationFragmentViewModel
import kotlinx.android.synthetic.main.email_authentication.*
import kotlinx.android.synthetic.main.other_authentication.*


/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class AuthenticationFragment : Fragment() {

    private var myListener: OnAuthenticationFragmentListener?= null
    private val authenticationFragmentViewModel: AuthenticationFragmentViewModel by viewModels()

    interface OnAuthenticationFragmentListener {
        fun onConnectionPressed(email: String)
        fun onGooglePressed()
        fun onFacebookPressed(token: AccessToken)
        fun onPrivacyPolicyPressed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(email_edit_text.text.toString().isNotEmpty())
            authenticationFragmentViewModel.email = email_edit_text.text.toString()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnAuthenticationFragmentListener)
            myListener = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email_edit_text.setText(authenticationFragmentViewModel.email)

        login_button.setOnClickListener {
            loginEmail()
        }

        // enter press edit text
        email_edit_text.setOnKeyListener{ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER)
                loginEmail()
            false
        }

        sign_in_google.setOnClickListener {
            myListener!!.onGooglePressed()
        }

        sign_in_facebook.setOnClickListener {
            login_button_facebook.performClick()
        }

        login_button_facebook.setOnClickListener {
            loginFacebook()
        }

        privacy_policy.setOnClickListener {
            myListener!!.onPrivacyPolicyPressed()
        }

        loadAnimation()

    }

    private fun loadAnimation() {

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            landscapeAnimation()
        } else {
            portraitAnimation()
        }


    }

    private fun portraitAnimation() {
        val leftAnim = AnimationUtils.loadAnimation(activity, R.anim.left_animation)
        val rightAnim = AnimationUtils.loadAnimation(activity, R.anim.right_animation)

        email_til.animation = leftAnim
        login_button.animation = leftAnim
        authentication.animation = leftAnim
        privacy_policy.animation = leftAnim

        sign_in_facebook.animation = rightAnim
        sign_in_google.animation = rightAnim
    }

    private fun landscapeAnimation() {
        val bottomAnim = AnimationUtils.loadAnimation(activity, R.anim.bottom_animation)
        val topAnim = AnimationUtils.loadAnimation(activity, R.anim.top_animation)

        email_til.animation = bottomAnim
        login_button.animation = bottomAnim
        authentication.animation = bottomAnim
        privacy_policy.animation = bottomAnim

        sign_in_facebook.animation = topAnim
        sign_in_google.animation = topAnim
    }

    private fun loginEmail() {

        authenticationFragmentViewModel.email = email_edit_text.text.toString()

        if(authenticationFragmentViewModel.validEmail()) {
            myListener!!.onConnectionPressed(authenticationFragmentViewModel.email)
            email_edit_text.error = null
        }
        else
            email_edit_text.error = resources.getString(R.string.invalid_email)
    }


    private fun loginFacebook() {

        val callbackManager = CallbackManager.Factory.create()

        // Callback registration
        with(login_button_facebook) {

            // Callback registration
            login_button_facebook.setReadPermissions("email", "public_profile")
            login_button_facebook.registerCallback(
                callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult) {
                        Log.d(TAG, "facebook:onSuccess:$result")
                        myListener!!.onFacebookPressed(result.accessToken)
                    }

                    override fun onCancel() {
                        Log.d(TAG, "facebook:onCancel")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d(TAG, "facebook:onError", error)
                    }
                }
            )
        }
    }


    companion object {
        const val TAG: String = "AuthenticationFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment AuthenticationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = AuthenticationFragment()

    }
}