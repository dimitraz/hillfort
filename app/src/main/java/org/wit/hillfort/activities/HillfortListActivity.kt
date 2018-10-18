package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.HillfortModel

class HillfortListActivity : AppCompatActivity(), HillfortListener, AnkoLogger {
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_hillfort_list)
    app = application as MainApp

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = HillfortAdapter(app.hillforts.findAll(), this)
  }

  // Add listener for when a hillfort card is pressed
  override fun onHillfortClick(hillfort: HillfortModel) {
    startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
  }

  // Add listener for when a hillfort card is long pressed
  override fun onHillfortLongClick(hillfort: HillfortModel): Boolean {
    toast("Long press detected")
    //    alert {
    //      titleResource = R.string.title
    //      messageResource = R.string.message
    //      positiveButton(R.string.ok) { dialog ->
    //        dialog.dismiss()
    //        app.hillforts.delete(hillfort)
    //        toast("$hillfort deleted")
    //      }
    //      negativeButton(R.string.cancel) { dialog ->
    //        dialog.dismiss()
    //        toast("Not deleting")
    //      }
    //      show()
    //    }
    //    return true
    return true
  }

  // Refresh the list when something changes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    recyclerView.adapter?.notifyDataSetChanged()
    super.onActivityResult(requestCode, resultCode, data)
  }
}