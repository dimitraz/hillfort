package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel

class LoginActivity : AppCompatActivity() {
  lateinit var app: MainApp
  var user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    toolbarLogin.title = title
    setSupportActionBar(toolbarLogin)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    btnLogin.setOnClickListener {
      user.email = userEmail.text.toString()
      user.password = userPassword.text.toString()

      if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
        val foundUser = app.users.authenticateUser(user.email, user.password)
        if (foundUser != null) {
          app.currentUser = foundUser
          startActivityForResult(intentFor<HillfortListActivity>(), 0)
        } else {
          toast(R.string.text_authFailed)
        }
      }
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}