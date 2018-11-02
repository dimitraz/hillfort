package org.wit.hillfort.models.user

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.mindrot.jbcrypt.BCrypt
import org.wit.hillfort.helpers.exists
import org.wit.hillfort.helpers.read
import org.wit.hillfort.helpers.write
import java.util.*

const val JSON_FILE = "hillfort_users.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomId(): Long {
  return Random().nextLong()
}

class UserJsonStore: UserStore, AnkoLogger {
  val context: Context
  var users = mutableListOf<UserModel>()

  constructor (context: Context) {
    this.context = context
    if (exists(context, JSON_FILE)) {
      deserialize()
    }
  }

  override fun findAll(): List<UserModel> {
    return users
  }

  override fun create(user: UserModel) {
    user.id = generateRandomId()
    users.add(user)
    serialize()
  }

  override fun update(user: UserModel) {
    var foundUser: UserModel? = users.find { h -> h.id == user.id }

    if (foundUser != null) {
      foundUser.name = user.name
      foundUser.surname = user.surname
      foundUser.email = user.email
      foundUser.password = user.password
      foundUser.profileImage = user.profileImage
    }
    serialize()
  }

  override fun delete(user: UserModel) {
    users.remove(user)
    serialize()
  }

  override fun userExists(email: String): Boolean {
    var foundUser: UserModel? = users.find { h -> h.email == email }
    return if (foundUser != null) {
      true
    } else {
      return false
    }
  }

  override fun authenticateUser(email: String, password: String): UserModel? {
    var foundUser: UserModel? = users.find { h -> h.email == email }
    if (foundUser != null) {
      if (BCrypt.checkpw(password, foundUser.password)) {
        return foundUser
      } else {
        return null
      }
    } else {
      return null
    }
  }

  override fun logAll() {
    users.forEach { info("$it") }
  }

  private fun serialize() {
    val jsonString = gsonBuilder.toJson(users, listType)
    write(context, JSON_FILE, jsonString)
  }

  private fun deserialize() {
    val jsonString = read(context, JSON_FILE)
    users = Gson().fromJson(jsonString, listType)
  }
}