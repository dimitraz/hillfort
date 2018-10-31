package org.wit.hillfort.models.hillfort

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun findVisited(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
  fun logAll()
}