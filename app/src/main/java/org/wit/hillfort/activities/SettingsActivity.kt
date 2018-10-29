package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.R
import org.wit.hillfort.helpers.validateEmail
import org.wit.hillfort.helpers.validatePassword
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel


class SettingsActivity : BaseActivity(), AnkoLogger {
  var user = UserModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_settings, null, false)
    drawer_layout.addView(contentView, 0)

    // Get logged in user
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

    // To do: Log out button
  }
}