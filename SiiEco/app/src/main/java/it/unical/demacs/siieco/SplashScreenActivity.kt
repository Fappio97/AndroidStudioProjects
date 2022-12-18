package it.unical.demacs.siieco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import it.unical.demacs.siieco.ui.splash_screen.SplashScreenViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenViewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_NoActionBar)
        super.onCreate(savedInstanceState)
        //full screen
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        //supportActionBar?.hide()

        loadSlogan()
        loadAnimation()
    }

    private fun loadSlogan() {
        val string = splashScreenViewModel.getSloganRandom(resources.getStringArray(R.array.slogan_array))
        slogan_text.text = string
    }

    private fun loadAnimation() {
        val topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        logo.animation = topAnim
        app_text.animation = bottomAnim
        slogan_text.animation = bottomAnim
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({

            val intent = if (splashScreenViewModel.checkCurrentUser() == null) {
                Intent(this, AuthenticationActivity::class.java)
            } else {
                Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 3000)

    }
}