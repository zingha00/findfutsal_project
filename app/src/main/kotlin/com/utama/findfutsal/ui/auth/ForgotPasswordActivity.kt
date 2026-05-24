package com.utama.findfutsal.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utama.findfutsal.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            // Logika reset password
            finish()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}