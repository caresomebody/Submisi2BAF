package com.caresomebody.test.submisi2fundamental.helper

import android.database.Cursor
import com.caresomebody.test.submisi2fundamental.FavoriteModel
import com.caresomebody.test.submisi2fundamental.database.UserContract

object MappingHelper {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteModel> {
            val favList = ArrayList<FavoriteModel>()
            favCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(UserContract.UserColumns.ID))
                    val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.USERNAME))
                    val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.AVATAR))
                    favList.add(FavoriteModel(id, username, avatar))
                }
            }
            return favList
        }

    fun mapCursorToObject(favCursor: Cursor?): FavoriteModel {
        var userFavorit = FavoriteModel()
        favCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(UserContract.UserColumns.ID))
            val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.AVATAR))
            userFavorit = FavoriteModel(id, username, avatar)
        }
        return userFavorit
    }
}