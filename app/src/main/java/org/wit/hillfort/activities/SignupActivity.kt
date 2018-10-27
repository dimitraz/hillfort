package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel

class SignupActivity: AppCompatActivity() {
  lateinit var app: MainApp
  val user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)
    toolbarSignup.title = title
    setSupportActionBar(toolbarSignup)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    // Create the user and start the app
    btnSignup.setOnClickListener {
      user.name = userName.text.toString()
      user.surname = userSurname.text.toString()
      user.email = userEmail.text.toString()
      user.password = userPassword.text.toString()

      if (user.name.isNotEmpty() && user.email.isNotEmpty() && user.password.isNotEmpty()) {
        if (app.users.userExists(user.email)) {
          toast(R.string.text_userExists)
        } else {
          // Hash and salt the user's password
          user.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
          app.users.create(user)
          app.currentUser = user
          startActivityForResult(intentFor<HillfortListActivity>(), 0)
        }
      }
      else {
        toast(R.string.text_invalidSignup)
      }
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}