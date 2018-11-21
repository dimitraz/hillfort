package org.wit.hillfort.views.landing

import org.jetbrains.anko.intentFor
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.views.login.LoginView
import org.wit.hillfort.views.signup.SignupView

class LandingPresenter(val view: LandingView) {
  val app: MainApp = view.application as MainApp

  fun doSignup() {
    view.startActivityForResult(view.intentFor<SignupView>(), 0)
  }

  fun doLogin() {
    view.startActivityForResult(view.intentFor<LoginView>(), 0)
  }
}