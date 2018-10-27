package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.helpers.CircleTransform
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel


class SettingsActivity : AppCompatActivity() {
  lateinit var app: MainApp
  var user = UserModel()
  val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_settings)
    toolbarSettings.title = title
    setSupportActionBar(toolbarSettings)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    user = app.currentUser!!
    profileName.text = "${user.name} ${user.surname}"

    // Add listener for choose profile button
    iconLayout.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
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

  // Activity lifecycle event, called when an activity finishes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
    // Recover profile when picker activity finishes
      IMAGE_REQUEST -> {
        if (data != null) {
          val uri = data?.data.toString()
          Picasso.get().load(uri)
              .transform(CircleTransform()).into(profileIcon)
        }
      }
    }
  }
}