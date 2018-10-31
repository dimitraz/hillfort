package org.wit.hillfort.models.hillfort

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.helpers.exists
import org.wit.hillfort.helpers.read
import org.wit.hillfort.helpers.write
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<HillfortModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class HillfortJsonStore: HillfortStore, AnkoLogger {
  private val userId: Long
  val context: Context
  var hillforts = mutableListOf<HillfortModel>()

  constructor (context: Context, userId: Long) {
    this.context = context
    this.userId = userId
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun findVisited(): List<HillfortModel> {
    return hillforts.filter { h -> h.visited }
  }

  override fun create(hillfort: HillfortModel) {
    hillfort.id = generateRandomId()
    hillfort.userId = userId
    hillforts.add(hillfort)
    serialize()
  }

  override fun update(hillfort: HillfortModel) {
    var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id }

    if (foundHillfort != null) {
      foundHillfort.userId = hillfort.userId
      foundHillfort.name = hillfort.name
      foundHillfort.description = hillfort.description
      foundHillfort.images = hillfort.images
      foundHillfort.location = hillfort.location
      foundHillfort.visited = hillfort.visited
      foundHillfort.date = hillfort.date
      foundHillfort.notes = hillfort.notes
    }

    serialize()
  }

  override fun delete(hillfort: HillfortModel) {
    hillforts.remove(hillfort)
    serialize()
  }

  override fun logAll() {
    hillforts.forEach { info("$it") }
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(hillforts, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    val allHillforts: List<HillfortModel> = Gson().fromJson(jsonString, listType)
    hillforts = allHillforts.filter { h -> h.userId == userId }.toMutableList()
  }
}