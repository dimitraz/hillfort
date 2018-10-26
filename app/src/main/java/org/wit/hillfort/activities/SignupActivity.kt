package org.wit.hillfort.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_signup.*
import org.wit.hillfort.R

class SignupActivity: AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)
    toolbarSignup.title = title
    setSupportActionBar(toolbarSignup)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_plain, menu)
    return super.onCreateOptionsMenu(menu)
  }
}