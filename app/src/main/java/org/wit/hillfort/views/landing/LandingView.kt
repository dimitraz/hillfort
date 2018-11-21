package org.wit.hillfort.views.landing

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_landing.*
import org.wit.hillfort.R

class LandingView: AppCompatActivity() {
  lateinit var presenter: LandingPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_landing)

    presenter = LandingPresenter(this)

    // Start signup activity when button is pressed
    btnSignup.setOnClickListener{
      presenter.doSignup()
    }

    // Start login activity when button is pressed
    btnLogin.setOnClickListener{
      presenter.doLogin()
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}