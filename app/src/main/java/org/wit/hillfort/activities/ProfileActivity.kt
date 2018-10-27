package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.wit.hillfort.R
import org.wit.hillfort.helpers.CircleTransform
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel


class ProfileActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp
  var user = UserModel()
  val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)
    toolbarProfile.title = title
    setSupportActionBar(toolbarProfile)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    user = app.currentUser!!
    profileName.text = "${user.name} ${user.surname}"

    if (user.profileImage.isNotEmpty()) {
      Picasso.get().load(user.profileImage)
          .transform(CircleTransform()).into(profileIcon)
    }

    // Add listener for choose profile button
    iconLayout.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_profile, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // Menu item selected
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_settings -> {
        startActivityForResult(intentFor<SettingsActivity>(), 0)
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
          user.profileImage = data.data.toString()
          Picasso.get().load(user.profileImage)
              .transform(CircleTransform()).into(profileIcon)
          app.users.update(user)
        }
      }
    }
  }
}