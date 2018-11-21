package org.wit.hillfort.views.hillfortList

import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.hillfort.main.MainApp
import org.wit.hillfort.models.hillfort.HillfortModel
import org.wit.hillfort.views.hillfort.HillfortView

class HillfortListPresenter(val view: HillfortListView) {
  val app: MainApp = view.application as MainApp

  fun getHillforts() = app.hillforts.findAll()

  fun doAddHillfort() {
    view.startActivityForResult<HillfortView>(0)
  }

  fun doEditHillfort(hillfort: HillfortModel) {
    view.startActivityForResult(view.intentFor<HillfortView>().putExtra("hillfort_edit", hillfort), 0)
  }
}