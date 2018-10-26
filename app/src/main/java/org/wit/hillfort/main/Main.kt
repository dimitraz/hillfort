package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.hillfort.HillfortJsonStore
import org.wit.hillfort.models.hillfort.HillfortStore

class MainApp : Application(), AnkoLogger {
  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJsonStore(applicationContext)
    info("Hillfort started")
  }
}