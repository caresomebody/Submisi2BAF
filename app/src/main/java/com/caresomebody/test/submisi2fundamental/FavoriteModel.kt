package com.caresomebody.test.submisi2fundamental

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteModel(
        var id: Int? = 0,
        var username: String = "",
        var avatar: String = "",
        var company: String = "",
        var location: String = ""
) : Parcelable