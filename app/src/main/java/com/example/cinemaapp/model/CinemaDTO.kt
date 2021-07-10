package com.example.cinemaapp.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CinemaDTO (
    var id: Int? = 0,
    var adult: Boolean? = false,
    var overview: String? = null,
    var poster_path:String? = null,
    var release_date: String? = null,
    var revenue : Int? = 0,
    var runtime: String? = null,
    var title: String? = null,
    var vote_average : Double? = 0.0,
    var vote_count : Int? = 0
):Parcelable