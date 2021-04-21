package com.caresomebody.test.submisi2fundamental.helper

import android.database.Cursor
import com.caresomebody.test.submisi2fundamental.data.FavoriteData
import com.caresomebody.test.submisi2fundamental.database.GitUserContract

object MappingHelper {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteData> {
            val favList = ArrayList<FavoriteData>()
            favCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(GitUserContract.UserColumns.ID))
                    val username = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.USERNAME))
                    val avatar = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.AVATAR))
                    favList.add(FavoriteData(id, username, avatar))
                }
            }
            return favList
        }

    fun mapCursorToObject(favCursor: Cursor?): FavoriteData {
        var userFavorit = FavoriteData()
        favCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(GitUserContract.UserColumns.ID))
            val username = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.AVATAR))
            userFavorit = FavoriteData(id, username, avatar)
        }
        return userFavorit
    }
}