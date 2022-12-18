package it.unical.demacs.siieco.ui.authentication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.viewModels
import it.unical.demacs.siieco.ui.authentication.EmailFragmentViewModel
import it.unical.demacs.siieco.R
import kotlinx.android.synthetic.main.fragment_email.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

class EmailFragment : Fragment() {

    private var myListener : OnEmailFragmentListener ?= null
    private val emailFragmentViewModel: EmailFragmentViewModel by viewModels()

    interface OnEmailFragmentListener {
        fun onResendEmail()
        fun onOpenEmailClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            emailFragmentViewModel.email = it.getString(ARG_PARAM1).toString()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_mail_text2.append(emailFragmentViewModel.boldEmail())

        resend_mail.setOnClickListener {
            resendEmail(it)
        }

        open_mail_client.setOnClickListener {
            myListener!!.onOpenEmailClient()
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

        image_email.animation = rightAnim
        send_mail_text1.animation = leftAnim
        send_mail_text2.animation = leftAnim
        resend_mail.animation = rightAnim
        open_mail_client.animation = rightAnim
    }

    private fun landscapeAnimation() {
        val leftAnim = AnimationUtils.loadAnimation(activity, R.anim.left_animation)
        val rightAnim = AnimationUtils.loadAnimation(activity, R.anim.right_animation)

        image_email.animation = rightAnim
        send_mail_text1.animation = leftAnim
        send_mail_text2.animation = leftAnim
        resend_mail.animation = leftAnim
        open_mail_client.animation = rightAnim
    }

    private fun resendEmail(view: View) {
        // se è stato già cliccato il pulsante
        // evita di inviare di nuovo la mail prima di 30 secondi
        if(!emailFragmentViewModel.resendEmail) {
            Toast.makeText(myListener!! as Context, getString(R.string.wait_30_seconds), Toast.LENGTH_SHORT).show()
        } else {
            synchronized(view) {
                emailFragmentViewModel.resendEmail = false
                myListener!!.onResendEmail()

                view.postDelayed({ emailFragmentViewModel.resendEmail = true }, 30000L)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEmailFragmentListener)
            myListener = context
    }

    companion object {
        const val TAG: String = "EmailFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment EmailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(string: String) =
            EmailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, string)
                }
            }
    }
}

