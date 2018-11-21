package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_landing.*
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.views.signup.SignupView

class LandingActivity: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_landing)

    // Start signup activity when button is pressed
    btnSignup.setOnClickListener{
      startActivityForResult(intentFor<SignupView>(), 0)
    }

    // Start login activity when button is pressed
    btnLogin.setOnClickListener{
      startActivityForResult(intentFor<LoginActivity>(), 0)
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }

}