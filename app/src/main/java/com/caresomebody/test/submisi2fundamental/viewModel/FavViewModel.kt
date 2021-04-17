package com.caresomebody.test.submisi2fundamental.viewModel

import androidx.lifecycle.ViewModel
import com.caresomebody.test.submisi2fundamental.FavoriteModel
import com.caresomebody.test.submisi2fundamental.database.UserDbHelper

class FavViewModel: ViewModel() {
    private var favorite: FavoriteModel? = null
    private lateinit var favHelper: UserDbHelper

    fun setFav(username: String, )
}