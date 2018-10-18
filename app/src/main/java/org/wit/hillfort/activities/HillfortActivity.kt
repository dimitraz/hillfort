package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.helpers.readImage
import org.wit.hillfort.helpers.readImageFromPath
import org.wit.hillfort.helpers.showImagePicker
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortActivity : AppCompatActivity(), AnkoLogger {
  lateinit var app: MainApp
  var hillfort = HillfortModel()
  val IMAGE_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp

    // Check if a hillfort has been passed in to be modified
    if (intent.hasExtra("hillfort_edit")) {
      hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")

      if (hillfort.image.isNotEmpty()) {
        chooseImage.setText(R.string.button_changeImagee)
      }

      hillfortName.setText(hillfort.name)
      hillfortDescription.setText(hillfort.description)
      hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
    }

    // Add listener for choose image button
    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
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

  // Recover image when picker activity finishes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          hillfort.image = data.getData().toString()
          hillfortImage.setImageBitmap(readImage(this, resultCode, data))
        }
      }
    }
  }
}
