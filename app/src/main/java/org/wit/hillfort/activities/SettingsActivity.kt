package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.R
import org.wit.hillfort.helpers.validateEmail
import org.wit.hillfort.helpers.validatePassword
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel


class SettingsActivity : AppCompatActivity() {
  lateinit var app: MainApp
  var user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    toolbarSettings.title = title
    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    user = app.currentUser!!

    // Prefill user credentials
    userName.setText(user.name)
    userSurname.setText(user.surname)
    userEmail.setText(user.email)

    // Update user credentials
    btnSave.setOnClickListener {
      var name = userName.text.toString()
      var surname = userSurname.text.toString()
      var email = userEmail.text.toString()
      var password = userPassword.text.toString()

      // Input validation
      if (name.isNotEmpty()) { user.name = name }
      if (surname.isNotEmpty()) { user.surname = surname }
      if (email.isNotEmpty() && validateEmail(email)) {
        user.email = email
      } else {
        toast("Invalid email")
      }
      if (password.isNotEmpty() && validatePassword(password)) {
        user.password = BCrypt.hashpw(password, BCrypt.gensalt())
      } else {
        toast("Invalid password")
      }

      toast("Saved")
      app.users.update(user)
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_settings, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // Menu item selected
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_logout -> {
        app.currentUser = null
        startActivityForResult(intentFor<LandingActivity>(), 0)
      }
    }
    return super.onOptionsItemSelected(item)
  }
}