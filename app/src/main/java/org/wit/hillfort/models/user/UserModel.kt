package org.wit.hillfort.models.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var id: Long = 0,
                     var name: String = "",
                     var surname: String = "",
                     var email: String = "",
                     var password: String = "",
                     var profileImage: String = "") : Parcelable