package org.wit.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {
  var app : MainApp? = null
  val hillfort = HillfortModel("Dun Aengus", "Prehistoric hillfort in the Aran Islands, County Galway")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    info("Hillfort: $hillfort")
  }
}
