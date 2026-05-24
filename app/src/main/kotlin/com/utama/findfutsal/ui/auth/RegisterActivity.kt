package com.utama.findfutsal.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.utama.findfutsal.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            // Logika registrasi
            finish()
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}