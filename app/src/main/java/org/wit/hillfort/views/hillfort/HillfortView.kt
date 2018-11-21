package org.wit.hillfort.views.hillfort


import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.R.id.item_delete
import org.wit.hillfort.activities.MapsActivity
import org.wit.hillfort.adapters.SliderAdapter
import org.wit.hillfort.helpers.showMultiImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.Date
import org.wit.hillfort.models.hillfort.HillfortModel
import org.wit.hillfort.models.hillfort.Location


class HillfortView : AppCompatActivity(), AnkoLogger {
  lateinit var presenter: HillfortPresenter
  var hillfort = HillfortModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)

    presenter = HillfortPresenter(this)

    // Start the image picker activity
    textAddImage.setOnClickListener {
      presenter.doSelectImages()
    }

    // Start the image picker activity
    chooseImage.setOnClickListener {
      presenter.doSelectImages()
    }

    // Start the map activity
    chooseLocation.setOnClickListener {
      presenter.doSetLocation()
    }

    // Update or create the hillfort object
    btnCreate.setOnClickListener {
      var name = hillfortName.text.toString()
      if (name.isNotEmpty()) {
        presenter.doAddOrSave(
            hillfortName.text.toString(),
            hillfortDescription.text.toString(),
            hillfortNotes.text.toString(),
            hillfortVisited.isChecked)
      } else {
        toast(R.string.error_invalidTitle)
      }
    }

    // Calendar date picker
    chooseDate.setOnClickListener {
      presenter.doSetDate()
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
//    if (edit) {
//      menu?.findItem(item_delete)?.isVisible = true
//    }
    return super.onCreateOptionsMenu(menu)
  }

  // Menu item selected
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> presenter.doCancel()
      R.id.item_delete -> {
        alert(R.string.message, R.string.title) {
          positiveButton(R.string.ok) {
            presenter.doDelete()
          }
          negativeButton(R.string.cancel) { dialog ->
            dialog.dismiss()
          }
          show()
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }

  // Load hillfort images
  fun loadImages(hillfort: HillfortModel) {
    showImages(hillfort.images)
  }

  // Show images in pager view
  fun showImages(images: MutableList<String>) {
    pager.adapter = SliderAdapter(applicationContext, images)
    pager.adapter?.notifyDataSetChanged()
  }

  fun showHillfort(hillfort: HillfortModel) {
    // Prefill fields
    btnCreate.setText(R.string.button_saveHillfort)
    hillfortName.setText(hillfort.name)
    hillfortDescription.setText(hillfort.description)
    hillfortNotes.setText(hillfort.notes)
    hillfortVisited.isChecked = hillfort.visited

    // Get date
    if (hillfort.date != null) {
      dateVisited.text = "${hillfort.date!!.day}/${hillfort.date!!.month}/${hillfort.date!!.year}"
    }

    // Hide the text view if there are images
    if (hillfort.images.isNotEmpty()) {
      textAddImage.visibility = View.GONE
      image.setText(R.string.button_changeImage)
    }

    // Load the list of images in a pager view
    loadImages(hillfort)
  }

  // Activity lifecycle event, called when an activity finishes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }
}
