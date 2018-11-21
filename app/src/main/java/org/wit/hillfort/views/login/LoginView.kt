package org.wit.hillfort.views.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast
import org.wit.hillfort.R

class LoginView : AppCompatActivity() {
  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    toolbarLogin.title = title
    setSupportActionBar(toolbarLogin)

    presenter = LoginPresenter(this)

    // Authenticate the user
    btnLogin.setOnClickListener {
      val email = userEmail.text.toString()
      val password = userPassword.text.toString()

      presenter.doLogin(email, password)
    }
  }

  // Show toast messages
  fun showToast(message: Int) {
    toast(message)
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}