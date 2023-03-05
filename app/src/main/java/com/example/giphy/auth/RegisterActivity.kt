package com.example.giphy.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.giphy.Utils.validateEmail
import com.example.giphy.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.signUp.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()

        when {
            email.isBlank() -> {
                binding.email.error = "Введите вашу почту"
                binding.email.requestFocus()
            }
            password.isBlank() -> {
                binding.password.error = "Введите ваш пароль"
                binding.password.requestFocus()
            }
            !email.validateEmail() -> {
                binding.email.error = "Некорректная почта"
                binding.email.requestFocus()
            }
            password.length < 5 -> {
                Toast.makeText(
                    baseContext,
                    "Пароль должен быть больше 5 символов",
                    Toast.LENGTH_SHORT
                ).show()
                binding.password.requestFocus()
            }
            else -> {
                createUser(email, password)
            }
        }

    }

    private fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if (!it.isSuccessful){
                        Toast.makeText(
                            baseContext, "Не удается создать аккаунт",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            baseContext, "Письмо отправлено. Подтвердите электронную почту",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        Toast.makeText(baseContext, "Слабый пароль", Toast.LENGTH_SHORT)
                            .show()
                        binding.password.requestFocus()
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                        binding.email.error = "Неверная почта"
                        binding.email.requestFocus()
                    }
                    is FirebaseAuthUserCollisionException -> {
                        binding.email.error = "Данная почта уже зарегистрирована!"
                        binding.email.requestFocus()
                    }
                    else -> {
                        Toast.makeText(baseContext, "Ошибка! ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }
}
