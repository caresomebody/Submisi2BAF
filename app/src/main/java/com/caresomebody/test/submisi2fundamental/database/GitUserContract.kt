package com.caresomebody.test.submisi2fundamental.database

import android.net.Uri
import android.provider.BaseColumns

internal class GitUserContract {
    companion object {
        const val AUTHORITY = "com.caresomebody.test.submisi2fundamental"
        const val SCHEME = "content"
    }
    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "FavoriteUser"
            const val ID = "_id"
            const val USERNAME = "username"
            const val AVATAR = "avatar_url"
            const val COMPANY = "company"
            const val LOCATION = "location"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}