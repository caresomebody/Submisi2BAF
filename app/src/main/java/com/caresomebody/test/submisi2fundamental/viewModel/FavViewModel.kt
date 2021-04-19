package com.caresomebody.test.submisi2fundamental.viewModel

import android.content.ContentValues
import android.content.Context
import androidx.lifecycle.ViewModel
import com.caresomebody.test.submisi2fundamental.FavoriteModel
import com.caresomebody.test.submisi2fundamental.database.UserContract
import com.caresomebody.test.submisi2fundamental.database.UserHelper

class FavViewModel(private val context: Context): ViewModel() {
    private var favorite: FavoriteModel? = null
    private lateinit var favHelper: UserHelper

    fun setFav(username: String, avatar: String){
        favHelper = UserHelper.getInstance(context)
        favHelper.open()
        val values = ContentValues()
        favorite
        values.put(UserContract.UserColumns.USERNAME, username)
        values.put(UserContract.UserColumns.AVATAR, avatar)
        val result = favHelper.insert(values)
        favorite?.id = result.toInt()
    }

    fun delFav(id: String){
        favHelper = UserHelper.getInstance(context)
        favHelper.open()
        favHelper.deleteById(id)
    }

    fun showRecylerList(){
        favHelper = UserHelper.getInstance(context)
        favHelper.open()
    }

    fun closeDB(){
        favHelper = UserHelper.getInstance(context)
        favHelper.open()
        favHelper.close()
    }
}