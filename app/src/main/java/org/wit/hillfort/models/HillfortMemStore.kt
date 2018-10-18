package org.wit.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
  return lastId++
}

class HillfortMemStore: HillfortStore, AnkoLogger {
  val hillforts = ArrayList<HillfortModel>()

  override fun findAll(): List<HillfortModel> {
   return hillforts
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = getId()
    hillfort.location = Location(52.245696, -7.139102, 15f)
    hillforts.add(hillfort)
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }

    if (foundHillfort != null) {
      foundHillfort.name = hillfort.name
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.location = hillfort.location
    }
  }

  override fun delete(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }

    if (foundHillfort != null) {
      hillforts.remove(hillfort)
    }
  }

  fun logAll() {
    hillforts.forEach { info("$it") }
  }
}