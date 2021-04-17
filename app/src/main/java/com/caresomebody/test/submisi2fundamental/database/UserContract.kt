package com.caresomebody.test.submisi2fundamental.database

import android.net.Uri
import android.provider.BaseColumns

internal class UserContract {
    companion object {
        const val AUTHORITY = "com.caresomebody.test.submisi2fundamental"
        const val SCHEME = "content"
    }
    internal class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "FavoriteUser"
            const val COLUMN_NAME_ID = "id"
            const val COLUMN_NAME_USERNAME = "username"
            const val COLUMN_NAME_AVATAR = "avatar_url"
            const val COLUMN_NAME_COMPANY = "company"
            const val COLUMN_NAME_LOCATION = "location"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }

    }
}