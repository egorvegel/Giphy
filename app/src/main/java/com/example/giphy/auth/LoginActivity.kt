package com.example.giphy.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giphy.MainActivity
import com.example.giphy.Utils.validateEmail
import com.example.giphy.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.signIn.setOnClickListener {
            performLogIn()
        }
    }

    private fun performLogIn() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        when {
            email.isBlank() -> {
                binding.email.error = "Введите почту"
                binding.email.requestFocus()
            }
            !email.validateEmail() -> {
                binding.email.error = "Введите корректную почту"
                binding.email.requestFocus()
            }
            binding.password.text.toString().isBlank() -> {
                Toast.makeText(baseContext, "Введите пароль", Toast.LENGTH_SHORT).show()
                binding.password.requestFocus()
            }
            else -> {
                authorize(email, password)
            }
        }
    }

    private fun authorize(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(
                        baseContext, "Успешно",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext, "Ошибка!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Неверная почта или пароль", Toast.LENGTH_SHORT).show()
            }
    }
}