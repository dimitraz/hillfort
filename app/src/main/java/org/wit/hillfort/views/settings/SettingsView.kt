package org.wit.hillfort.views.settings

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.activities.BaseActivity
import org.wit.hillfort.models.user.UserModel

class SettingsView : BaseActivity() {
  private lateinit var presenter: SettingsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_settings, null, false)
    drawer_layout.addView(contentView, 0)

    presenter = SettingsPresenter(this)

    // Update user credentials
    btnSave.setOnClickListener {
      var name = userName.text.toString()
      var surname = userSurname.text.toString()
      var email = userEmail.text.toString()
      var password = userPassword.text.toString()
      presenter.doSaveUser(name, surname, email, password)
    }

    // To do: Log out button
  }

  fun showUser(user: UserModel) {
    userName.setText(user.name)
    userSurname.setText(user.surname)
    userEmail.setText(user.email)
  }

  fun showToast(message: String) {
    toast(message)
  }
}