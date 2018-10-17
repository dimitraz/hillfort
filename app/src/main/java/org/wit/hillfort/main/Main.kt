package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.HillfortMemStore
import org.wit.hillfort.models.HillfortModel

class MainApp : Application(), AnkoLogger {
  val hillforts = HillfortMemStore()

  override fun onCreate() {
    super.onCreate()
    info("Hillfort started")

    hillforts.create(HillfortModel(name = "One", description = "About one..."))
    hillforts.create(HillfortModel(name = "Two", description = "About two..."))
    hillforts.create(HillfortModel(name = "Three", description = "About three..."))
  }
}