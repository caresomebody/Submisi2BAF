package com.caresomebody.test.submisi2fundamental.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitUser(
        var id: Int = 0,
        var name: String = "",
        var username: String = "",
        var company: String = "",
        var location: String = "",
        var repository: Int = 0,
        var followers: Int = 0,
        var following: Int = 0,
        var avatar: String = ""
) : Parcelable
