package com.caresomebody.test.submisi2fundamental.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteData(
        var id: Int = 0,
        var username: String? = "",
        var avatar: String? = "",
        var company: String? = "",
        var location: String? = ""
) : Parcelable