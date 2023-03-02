package com.example.giphy.utils

import android.widget.EditText

object Utils {
    fun String.validateEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    fun EditText.isInvalid() = this.text.toString().isBlank()
}