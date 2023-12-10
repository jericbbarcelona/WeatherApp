package com.jbarcelona.weatherapp.util

import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidUtil {
    private const val REGEX_EMAIL = "(?:[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-zA-Z0-9-]*[a-zA-Z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    private const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[#\$@!%&*?])[A-Za-z\\d#\$@!%&*?]{8,}\$"

    fun isEmpty(string: String?): Boolean {
        return string == null || string.isEmpty()
    }

    fun isValidEmail(email: String): Boolean {
        if (isEmpty(email)) {
            return false
        }
        val pattern = Pattern.compile(REGEX_EMAIL)
        return pattern.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        val matcher: Matcher
        val pattern: Pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)
        return matcher.matches()
    }
}