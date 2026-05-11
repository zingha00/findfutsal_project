package com.utama.findfutsal.

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

        // Jika sudah login, langsung ke HomeActivity
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        etIdentifier = findViewById(R.id.etIdentifier)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvDaftar = findViewById(R.id.tvDaftar)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)

        btnLogin.setOnClickListener {
            loginUser()
        }

        tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        tvLupaPassword.setOnClickListener {
            Toast.makeText(this, "Fitur lupa password belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser() {
        val identifier = etIdentifier.text.toString().trim()
        val password = etPassword.text.toString().trim()

        var isValid = true

        if (TextUtils.isEmpty(identifier)) {
            etIdentifier.error = "Email atau nomor HP harus diisi!"
            etIdentifier.requestFocus()
            isValid = false
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.error = "Password harus diisi!"
            if (isValid) etPassword.requestFocus()
            isValid = false
        }

        if (!isValid) return

        // Ambil data dari SharedPreferences
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPhone = sharedPreferences.getString("phone", "")
        val savedPassword = sharedPreferences.getString("password", "")
        val savedName = sharedPreferences.getString("namaLengkap", "")

        val isIdentifierMatch = identifier == savedEmail || identifier == savedPhone || identifier == savedName

        if (isIdentifierMatch && password == savedPassword) {
            sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
            Toast.makeText(this, "Login berhasil! Selamat datang, $savedName", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Email/No HP/Nama atau password salah!", Toast.LENGTH_SHORT).show()
        }
    }
}