package com.caresomebody.test.submisi2fundamental.helper

import android.database.Cursor
import com.caresomebody.test.submisi2fundamental.FavoriteModel
import com.caresomebody.test.submisi2fundamental.database.UserContract

object MappingHelper {
        fun mapCursorToArrayList(favCursor: Cursor?): ArrayList<FavoriteModel> {
            val favList = ArrayList<FavoriteModel>()
            favCursor?.apply {
                while (moveToNext()) {
                    val id = getInt(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_ID))
                    val username = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_USERNAME))
                    favList.add(FavoriteModel(id, username))
                }
            }
            return favList
        }
}