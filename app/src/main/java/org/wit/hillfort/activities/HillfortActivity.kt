package org.wit.hillfort.activities


import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.adapters.SliderAdapter
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.HillfortModel
import org.wit.hillfort.models.hillfort.Location

class HillfortActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp
  lateinit var adapter: PagerAdapter
  var hillfort = HillfortModel()
  var edit = false
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    app = application as MainApp

    // Check if a hillfort has been passed in to be modified
    if (intent.hasExtra("hillfort_edit")) {
      edit = true
      btnCreate.setText(R.string.button_saveHillfort)
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")

      if (hillfort.images.isNotEmpty()) {
        chooseImage.setText(R.string.button_changeImagee)
      }

      hillfortName.setText(hillfort.name)
      hillfortDescription.setText(hillfort.description)
    }

    // Load the list of images in a pager view
    loadImages()

    // Add listener for choose image button
    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    // Add listener for choose location button
    chooseLocation.setOnClickListener {
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", hillfort.location), LOCATION_REQUEST)
    }

    // Add listener for create button
    btnCreate.setOnClickListener {
      hillfort.name = hillfortName.text.toString()
      hillfort.description = hillfortDescription.text.toString()

      // Update or create the hillfort object
      if (hillfort.name.isNotEmpty()) {
        if (edit) {
          app.hillforts.update(hillfort.copy())
          app.hillforts.logAll()
        } else {
          app.hillforts.create(hillfort.copy())
          app.hillforts.logAll()
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast(R.string.name_error)
      }
    }
  }

  // Inflate the menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_hillfort, menu)
    return super.onCreateOptionsMenu(menu)
  }

  // Menu item selected
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_delete -> {
        alert(R.string.message, R.string.title) {
          positiveButton(R.string.ok) {
            app.hillforts.delete(hillfort)
            debug("$hillfort deleted")
            finish()
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
  private fun loadImages() {
    showImages(hillfort.images)
  }

  // Show images in pager view
  fun showImages(images: MutableList<String>) {
    pager.adapter = SliderAdapter(applicationContext, images)
    pager.adapter?.notifyDataSetChanged()
  }

  // Activity lifecycle event, called when an activity finishes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      // Recover image when picker activity finishes
      IMAGE_REQUEST -> {
        if (data != null) {
          val clipData = data.clipData

          // Handle multiple photos
          if (clipData != null) {
            hillfort.images.clear()
            for (i in 0 until clipData.itemCount) {
              val uri = clipData.getItemAt(i).uri.toString()
              hillfort.images.add(uri)
            }
            loadImages()
          }
          // Handle single photo
          else {
            val uri = data?.data.toString()
            hillfort.images.clear()
            hillfort.images.add(uri)
            loadImages()
          }
        }
      }
      // Recover location when map activity finishes
      LOCATION_REQUEST -> {
        if (data != null) {
          hillfort.location = data.extras.getParcelable<Location>("location")
        }
      }
    }
  }
}
