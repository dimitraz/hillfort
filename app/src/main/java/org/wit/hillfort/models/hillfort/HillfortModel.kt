package org.wit.hillfort.models.hillfort

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class HillfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String = "",
                         var images: MutableList<String> = ArrayList(),
                         var location: Location = Location(),
                         var visited: Boolean = false): Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable