package org.wit.hillfort.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class HillfortModel(var id: Long = 0,
                         var name: String = "",
                         var description: String = "",
                         var image: String = "",
                         var location: Location = Location()): Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable