package it.unical.demacs.myapplication

import android.content.Context
import android.opengl.Visibility
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment(), View.OnFocusChangeListener {

    private var listener: OnLoginFragmentInteractionListener? = null
    private var errorVisibile = false

    companion object {
        const val TAG = "LoginFragment"
        
        @JvmStatic
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.login_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userNameTextView.onFocusChangeListener = this
        passwordTextView.onFocusChangeListener = this
        loginButton.setOnClickListener {
 //           Log.d("DEBUG", "${userNameTextView.text} + ${passwordTextView.text}")
            errorVisibile = false
            if(userNameTextView.text.isEmpty()) {
                userNameErrorView.visibility = View.VISIBLE
                errorVisibile = true
            } else
                userNameErrorView.visibility = View.GONE
            if(passwordTextView.text.isEmpty()) {
                passwordErrorView.visibility = View.VISIBLE
                errorVisibile = true
            } else {
                passwordErrorView.visibility = View.GONE
            }
            if(!errorVisibile && !Login.instance.checkUserExist(userNameTextView.text.toString(), passwordTextView.text.toString())) {
                wrongCredential.visibility = View.VISIBLE
                errorVisibile = true
            } else {
                wrongCredential.visibility = View.GONE
            }
            if(!errorVisibile) {
                listener?.onLoginButtonPressed(userNameTextView.text.toString())

                userNameTextView.text.clear()
                passwordTextView.text.clear()
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnLoginFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLoginFragmentInteractionListener")
        }
    }

    interface OnLoginFragmentInteractionListener {
        fun onLoginButtonPressed(username: String)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onFocusChange(p0: View?, p1: Boolean) {
        if(p1) {
            (p0 as EditText).hint = ""
        } else {
            when(p0?.id) {
                userNameTextView.id -> (p0 as EditText).hint = getString(R.string.inserisci_username_o_email)
                passwordTextView.id -> (p0 as EditText).hint = getString(R.string.inserisci_password)
            }
        }
    }


}