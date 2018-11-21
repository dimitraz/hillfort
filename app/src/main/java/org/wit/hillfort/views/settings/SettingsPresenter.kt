package org.wit.hillfort.views.settings

import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.helpers.validateEmail
import org.wit.hillfort.helpers.validatePassword
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel

class SettingsPresenter(val view: SettingsView) {
  var app: MainApp = view.application as MainApp
  var user = UserModel()

  init {
    user = app.currentUser!!
    view.showUser(user)
  }

  fun doSaveUser(name: String, surname: String, email: String, password: String) {
    if (name.isNotEmpty()) { user.name = name }
    if (surname.isNotEmpty()) { user.surname = surname }
    if (email.isNotEmpty() && validateEmail(email)) {
      user.email = email
    } else {
      view.showToast("Invalid email")
    }
    if (password.isNotEmpty() && validatePassword(password)) {
      user.password = BCrypt.hashpw(password, BCrypt.gensalt())
    } else if (password.isNotEmpty() && !validatePassword(password)){
      view.showToast("Invalid password")
    }

    view.showUser(user)
    view.showToast("User saved")
    app.users.update(user)
  }
}