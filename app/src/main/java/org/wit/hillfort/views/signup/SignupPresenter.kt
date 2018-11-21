package org.wit.hillfort.views.signup

import org.jetbrains.anko.intentFor
import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.R
import org.wit.hillfort.activities.HillfortListActivity
import org.wit.hillfort.helpers.validateEmail
import org.wit.hillfort.helpers.validatePassword
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.HillfortJsonStore
import org.wit.hillfort.models.user.UserModel

class SignupPresenter(val view: SignupView) {
  var app: MainApp = view.application as MainApp
  var user = UserModel()

  fun doCreateUser(name: String, surname: String, email: String, password: String) {
    if (name.isNotEmpty() && validateEmail(email) && validatePassword(password)) {
      if (app.users.userExists(email)) {
        view.showToast(R.string.error_userExists)
      } else {
        // Hash and salt the user's password
        user.name = name
        user.email = email
        user.surname = surname
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt())
        app.users.create(user)
        app.currentUser = user
        app.hillforts = HillfortJsonStore(view.applicationContext, user.id)
        view.startActivityForResult(view.intentFor<HillfortListActivity>(), 0)
      }
    }
    else {
      // Add an email validation error message
      if (!validateEmail(email)) { view.showEmailError() }

      // Add a password validation error message
      if (!validatePassword(password)) { view.showPasswordError() }

      // Add a name validation error message
      if (name.isEmpty()) { view.showNameError() }

      view.showToast(R.string.error_invalidSignup)
    }
  }
}