package com.mantushnikita.movieapp.util.validation

import android.util.Patterns
import com.mantushnikita.mathpro.util.validation.ValidationResult
import com.mantushnikita.movieapp.R

fun validateEmail(email: String): ValidationResult {
    return when {
        email.isBlank() -> ValidationResult.Invalid(R.string.email_empty)

        !Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() -> ValidationResult.Invalid(R.string.email_incorrect_format)

        else -> ValidationResult.Valid
    }
}

fun validatePassword(password: String): ValidationResult {
    val passwordPattern =
        Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)")
    return when {
        password.isBlank() -> ValidationResult.Invalid(R.string.password_empty)

        password.length < 6 || password.length > 50 -> ValidationResult.Invalid(R.string.password_length)

        !passwordPattern.containsMatchIn(password) -> ValidationResult.Invalid(R.string.password_special_symbol)

        else -> ValidationResult.Valid
    }
}

fun validateName(name: String): ValidationResult {
    return when {
        name.isBlank() -> ValidationResult.Invalid(R.string.field_empty)

        name.length < 4 || name.length > 50 -> ValidationResult.Invalid(R.string.field_length)

        else -> ValidationResult.Valid
    }
}

fun validateEmptyField(field: String): ValidationResult {
    return when {
        field.isBlank() -> ValidationResult.Invalid(R.string.field_empty)

        else -> ValidationResult.Valid
    }
}