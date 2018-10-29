package org.wit.hillfort.models.hillfort

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class HillfortModel(var id: Long = 0,
                         var userId: Long = 0,
                         var name: String = "",
                         var description: String = "",
                         var images: MutableList<String> = ArrayList(),
                         var location: Location = Location(),
                         var visited: Boolean = false,
                         var date: Date? = null,
                         var notes: String = "") : Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Date(var day: Int, var month: Int, var year: Int) : Parcelable