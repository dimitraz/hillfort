package org.wit.hillfort.views.login

import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.activities.HillfortListActivity
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.HillfortJsonStore
import org.wit.hillfort.models.user.UserModel

class LoginPresenter(val view: LoginView) {
  var app: MainApp = view.application as MainApp
  var user = UserModel()

  fun doLogin(email: String, password: String) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
      val foundUser = app.users.authenticateUser(email, password)
      if (foundUser != null) {
        app.currentUser = foundUser
        app.hillforts = HillfortJsonStore(view.applicationContext, foundUser.id)
        view.startActivityForResult(view.intentFor<HillfortListActivity>(), 0)
      } else {
        view.showToast(R.string.text_authFailed)
      }
    }
  }
}