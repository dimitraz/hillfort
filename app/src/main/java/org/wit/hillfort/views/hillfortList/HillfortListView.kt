package org.wit.hillfort.views.hillfortList

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.activity_base.*
import org.jetbrains.anko.*
import org.wit.hillfort.R
import org.wit.hillfort.activities.BaseActivity
import org.wit.hillfort.adapters.HillfortAdapter
import org.wit.hillfort.adapters.HillfortListener
import org.wit.hillfort.models.hillfort.HillfortModel


class HillfortListView : BaseActivity(), HillfortListener, AnkoLogger {
  lateinit var presenter: HillfortListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val contentView = layoutInflater.inflate(R.layout.activity_hillfort_list, null, false)
    drawer_layout.addView(contentView, 0)

    presenter = HillfortListPresenter(this)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadHillforts()
  }

  // Load hillforts
  private fun loadHillforts() {
    showHillforts(presenter.getHillforts())
  }

  // Show hillforts in recyler view
  private fun showHillforts (hillforts: List<HillfortModel>) {
    recyclerView.adapter = HillfortAdapter(this, hillforts, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  // Add listener for when a hillfort card is pressed
  override fun onHillfortClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  // Refresh the list when something changes
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }
}