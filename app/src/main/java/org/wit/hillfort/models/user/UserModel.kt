package org.wit.hillfort.models.user

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserModel(var id: Long = 0,
                         var name: String = "",
                         var surname: String = "",
                         var email: String = "",
                         var password: String = "")