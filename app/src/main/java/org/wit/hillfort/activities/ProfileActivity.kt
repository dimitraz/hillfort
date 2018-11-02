package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.wit.hillfort.R
import org.wit.hillfort.helpers.CircleTransform
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.models.user.UserModel


class ProfileActivity : BaseActivity() {
  var user = UserModel()
  private val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_profile, null, false)
    drawer_layout.addView(contentView, 0)

    // Get logged in user
    user = app.currentUser!!
    profileName.text = "${user.name} ${user.surname}"

    // Load profile image
    if (user.profileImage.isNotEmpty()) {
      Picasso.get().load(user.profileImage)
          .transform(CircleTransform()).into(profileIcon)
    }

    // Load profile stats
    totalHillforts.text = "Total hillforts: ${app.hillforts.findAll().size}"
    totalVisited.text = "Total visited hillforts: ${app.hillforts.findVisited().size}"

    // Start image picker for profile image
    iconLayout.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }
  }

  // Recover the profile image when the image picker finishes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
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