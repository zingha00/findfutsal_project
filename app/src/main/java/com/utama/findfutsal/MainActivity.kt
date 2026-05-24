package com.utama.findfutsal

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etIdentifier: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvDaftar: TextView
    private lateinit var tvLupaPassword: TextView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("TugasApp", MODE_PRIVATE)

        // Cek status login
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            // Uncomment jika HomeActivity sudah ada
            // startActivity(Intent(this, HomeActivity::class.java))
            // finish()
        }

        setContentView(R.layout.activity_main)

        // Inisialisasi View
        etIdentifier = findViewById(R.id.etIdentifier)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvDaftar = findViewById(R.id.tvDaftar)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)

        btnLogin.setOnClickListener {
            loginUser()
        }

        tvDaftar.setOnClickListener {
            // Intent ke RegisterActivity
            // startActivity(Intent(this, RegisterActivity::class.java))
            Toast.makeText(this, "Menuju halaman Daftar", Toast.LENGTH_SHORT).show()
        }

        tvLupaPassword.setOnClickListener {
            Toast.makeText(this, "Fitur lupa sandi belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val identifier = etIdentifier.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (TextUtils.isEmpty(identifier)) {
            etIdentifier.error = "Email atau nomor HP harus diisi!"
            return
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Kata sandi harus diisi!"
            return
        }

        // Contoh verifikasi sederhana
        val savedEmail = sharedPreferences.getString("email", "admin@gmail.com")
        val savedPassword = sharedPreferences.getString("password", "admin123")

        if ((identifier == savedEmail || identifier == "admin") && password == savedPassword) {
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, HomeActivity::class.java))
            // finish()
        } else {
            Toast.makeText(this, "Email atau kata sandi salah!", Toast.LENGTH_SHORT).show()
        }
    }
}
