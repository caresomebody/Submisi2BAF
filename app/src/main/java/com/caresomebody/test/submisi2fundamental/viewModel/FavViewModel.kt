package com.caresomebody.test.submisi2fundamental.viewModel

import android.content.ContentValues
import android.content.Context
import androidx.lifecycle.ViewModel
import com.caresomebody.test.submisi2fundamental.data.FavoriteData
import com.caresomebody.test.submisi2fundamental.database.GitUserContract
import com.caresomebody.test.submisi2fundamental.database.GitUserHelper

class FavViewModel(private val context: Context): ViewModel() {
    private var favorite: FavoriteData? = null
    private lateinit var favHelperGit: GitUserHelper

    fun setFav(username: String, avatar: String){
        favHelperGit = GitUserHelper.getInstance(context)
        favHelperGit.open()
        val values = ContentValues()
        favorite
        values.put(GitUserContract.UserColumns.USERNAME, username)
        values.put(GitUserContract.UserColumns.AVATAR, avatar)
        val result = favHelperGit.insert(values)
        favorite?.id = result.toInt()
    }

    fun delFav(id: String){
        favHelperGit = GitUserHelper.getInstance(context)
        favHelperGit.open()
        favHelperGit.deleteById(id)
    }

    fun showRecylerList(){
        favHelperGit = GitUserHelper.getInstance(context)
        favHelperGit.open()
    }

    fun closeDB(){
        favHelperGit = GitUserHelper.getInstance(context)
        favHelperGit.open()
        favHelperGit.close()
    }
}