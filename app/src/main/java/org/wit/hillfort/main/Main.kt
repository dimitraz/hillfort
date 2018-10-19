package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.HillfortJsonStore
import org.wit.hillfort.models.HillfortModel
import org.wit.hillfort.models.HillfortStore

class MainApp : Application(), AnkoLogger {
  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortJsonStore(applicationContext)
    info("Hillfort started")

    hillforts.create(HillfortModel(name = "One", description = "About one..."))
    hillforts.create(HillfortModel(name = "Two", description = "About two..."))
    hillforts.create(HillfortModel(name = "Three", description = "About three..."))
  }
}