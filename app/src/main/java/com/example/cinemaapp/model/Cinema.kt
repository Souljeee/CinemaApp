package com.example.cinemaapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cinema(
    var id: Int? = 0,
    var name: String? = "Name",
    var releaseDate: String? = "",
    var poster: String? = null ,
    var rating: String = "Rating",
    var duration: String = "",
    var description: String? = "",
    var revenue: String = "",
    var type: String = "",
) : Parcelable


