package it.unical.demacs.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import it.unical.demacs.service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startStopService()
        }
    }

    private fun startStopService() {
        TODO("Not yet implemented")
    }
}