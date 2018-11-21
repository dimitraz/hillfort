package org.wit.hillfort.views.hillfort

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import org.jetbrains.anko.intentFor
import org.wit.hillfort.activities.MapsActivity
import org.wit.hillfort.helpers.showMultiImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.Date
import org.wit.hillfort.models.hillfort.HillfortModel
import org.wit.hillfort.models.hillfort.Location

class HillfortPresenter(val view: HillfortView) {
  private val LOCATION_REQUEST = 2
  private val IMAGE_REQUEST = 1

  var hillfort = HillfortModel()
  var app: MainApp = view.application as MainApp
  var edit = false

  init {
    if (view.intent.hasExtra("hillfort_edit")) {
      edit = true
      hillfort = view.intent.extras.getParcelable<HillfortModel>("hillfort_edit")
      view.showHillfort(hillfort)
    }
  }

  // Function for adding or updating a hillfort
  fun doAddOrSave(name: String, description: String, notes: String, visited: Boolean) {
    hillfort.name = name
    hillfort.description = description
    hillfort.notes = notes
    hillfort.visited = visited

    if (edit) {
      app.hillforts.update(hillfort)
    } else {
      app.hillforts.create(hillfort)
    }
    view.finish()
  }

  fun doCancel() {
    view.finish()
  }

  fun doDelete() {
    app.hillforts.delete(hillfort)
    view.finish()
  }

  fun doSelectImages() {
    showMultiImagePicker(view, IMAGE_REQUEST)
  }

  fun doSetLocation() {
    if (hillfort.location.zoom == 0f) {
      hillfort.location = Location(52.245696, -7.139102, 15f)
    }
    view.startActivityForResult(view.intentFor<MapsActivity>().putExtra("location", hillfort.location), LOCATION_REQUEST)
  }

  fun doSetDate() {
    val calendar = Calendar.getInstance()
    val y = hillfort.date?.year ?: calendar.get(Calendar.YEAR)
    val m = hillfort.date?.month ?: calendar.get(Calendar.MONTH)
    val d = hillfort.date?.day ?: calendar.get(Calendar.DAY_OF_MONTH)

    val dialog = DatePickerDialog(view, DatePickerDialog.OnDateSetListener { _, year, month, day ->
      // dateVisited.text = "$day/$month/$year"
      hillfort.date = Date(day, month, year)
      view.showHillfort(hillfort)
    }, y, m, d)
    dialog.show()
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data.clipData != null) {
          hillfort.images.clear()
          for (i in 0 until data.clipData.itemCount) {
            val uri = data.clipData.getItemAt(i).uri.toString()
            hillfort.images.add(uri)
          }
        }
        // Handle single photo
        else {
          val uri = data.data.toString()
          hillfort.images.clear()
          hillfort.images.add(uri)
        }

        view.loadImages(hillfort)
      }
      LOCATION_REQUEST -> {
        hillfort.location = data.extras.getParcelable<Location>("location")
      }
    }
  }
}