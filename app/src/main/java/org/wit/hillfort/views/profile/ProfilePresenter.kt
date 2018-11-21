package org.wit.hillfort.views.profile

import android.content.Intent
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.user.UserModel

class ProfilePresenter(val view: ProfileView) {
  private val IMAGE_REQUEST = 1

  var app: MainApp = view.application as MainApp
  var user = UserModel()

  init {
    user = app.currentUser!!
    view.showProfile(user)
  }

  fun doSelectImage() {
    showImagePicker(view, IMAGE_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          user.profileImage = data.data.toString()
          view.showProfile(user)
          app.users.update(user)
        }
      }
    }
  }
}