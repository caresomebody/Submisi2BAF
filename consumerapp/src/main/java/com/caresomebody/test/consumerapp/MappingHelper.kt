package com.caresomebody.test.consumerapp

import android.database.Cursor
import com.caresomebody.test.consumerapp.FavoriteModel
import com.caresomebody.test.consumerapp.GitUserContract

object MappingHelper {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteModel> {
            val favList = ArrayList<FavoriteModel>()
            favCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(GitUserContract.UserColumns.ID))
                    val username = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.USERNAME))
                    val avatar = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.AVATAR))
                    favList.add(FavoriteModel(id, username, avatar))
                }
            }
            return favList
        }

    fun mapCursorToObject(favCursor: Cursor?): FavoriteModel {
        var userFavorit = FavoriteModel()
        favCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(GitUserContract.UserColumns.ID))
            val username = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.USERNAME))
            val avatar = getString(getColumnIndexOrThrow(GitUserContract.UserColumns.AVATAR))
            userFavorit = FavoriteModel(id, username, avatar)
        }
        return userFavorit
    }
}