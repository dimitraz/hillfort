package org.wit.hillfort.models.user

interface UserStore {
  fun findAll(): List<UserModel>
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun delete(user: UserModel)
  fun userExists(email: String): Boolean
  fun authenticateUser(email: String, password: String): UserModel?
  fun logAll()
}