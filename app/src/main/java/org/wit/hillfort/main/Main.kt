package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.hillfort.HillfortJsonStore
import org.wit.hillfort.models.hillfort.HillfortStore
import org.wit.hillfort.models.user.UserJsonStore
import org.wit.hillfort.models.user.UserModel
import org.wit.hillfort.models.user.UserStore

class MainApp : Application(), AnkoLogger {
  lateinit var hillforts: HillfortStore
  lateinit var users: UserStore
  var currentUser: UserModel? = null

  override fun onCreate() {
    super.onCreate()
    users = UserJsonStore(applicationContext)
    info("Hillfort started")
  }
}