package org.wit.hillfort.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp
  var hillfort = HillfortModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    app = application as MainApp

    // Check if a hillfort has been passed in to be modified
    if (intent.hasExtra("hillfort_edit")) {
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      hillfortName.setText(hillfort.name)
      hillfortDescription.setText(hillfort.description)
    }

    // Add listener for save button
    btnSave.setOnClickListener {
      hillfort.name = hillfortName.text.toString()
      hillfort.description = hillfortDescription.text.toString()

      // Update the hillfort object
      if (hillfort.name.isNotEmpty()) {
        app.hillforts.update(hillfort.copy())
        app.hillforts.logAll()

        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast(R.string.name_error)
      }
    }

    info("Hillfort: $hillfort")
  }
}
