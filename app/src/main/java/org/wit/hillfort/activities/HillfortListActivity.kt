package org.wit.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_base.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.adapters.HillfortAdapter
import org.wit.hillfort.adapters.HillfortListener
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.HillfortModel


class HillfortListActivity : BaseActivity(), HillfortListener, AnkoLogger {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val contentView = layoutInflater.inflate(R.layout.activity_hillfort_list, null, false)
    drawer_layout.addView(contentView, 0)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadHillforts()

    info(app.currentUser)
  }

  // Load hillforts
  private fun loadHillforts() {
    showHillforts(app.hillforts.findAll())
  }

  // Show hillforts in recyler view
  fun showHillforts (hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
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
    loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }
}