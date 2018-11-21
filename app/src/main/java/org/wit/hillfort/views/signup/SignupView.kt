package org.wit.hillfort.views.signup

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.models.user.UserModel

class SignupView: AppCompatActivity() {
  lateinit var presenter: SignupPresenter
  val user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)
    toolbarSignup.title = title
    setSupportActionBar(toolbarSignup)

    presenter = SignupPresenter(this)

    // Create the user and start the app
    btnSignup.setOnClickListener {
      val name = userName.text.toString()
      val surname = userSurname.text.toString()
      val email = userEmail.text.toString()
      val password = userPassword.text.toString()

      // Create the user if credentials are valid and start the app
      presenter.doCreateUser(name, surname, email, password)
    }
  }

  // Show toast messages
  fun showToast(message: Int) {
    toast(message)
  }

  // Show validation errors
  fun showPasswordError() { userPassword.error = getString(R.string.error_invalidPassword) }
  fun showNameError() { userName.error = getString(R.string.error_invalidName) }
  fun showEmailError() { userEmail.error = getString(R.string.error_invalidEmail) }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}