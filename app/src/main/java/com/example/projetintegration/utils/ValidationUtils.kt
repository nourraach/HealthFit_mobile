package com.example.projetintegration.utils

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

object ValidationUtils {
    
    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun isValidPhone(phone: String): Boolean {
        // More flexible phone validation - accepts various formats
        val cleanPhone = phone.replace(Regex("[^0-9]"), "") // Remove non-digits
        
        // Accept 10 digits starting with 0 (French format) or 8-15 digits (international)
        return when {
            cleanPhone.matches("^0[1-9][0-9]{8}$".toRegex()) -> true // French format: 0XXXXXXXXX
            cleanPhone.matches("^[1-9][0-9]{7,14}$".toRegex()) -> true // International format
            cleanPhone.length in 8..15 -> true // General international range
            else -> false
        }
    }
    
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    
    fun isValidDate(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.isLenient = false
            val parsedDate = sdf.parse(date)
            parsedDate != null && parsedDate.before(Date())
        } catch (e: Exception) {
            false
        }
    }
    
    fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }
}
