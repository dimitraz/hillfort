package org.wit.hillfort.helpers

// Helper function for validating email strings
fun validateEmail(email: String): Boolean {
  return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

// Helper function for validating passwords
fun validatePassword(password: String): Boolean {
  return password.length > 5
}