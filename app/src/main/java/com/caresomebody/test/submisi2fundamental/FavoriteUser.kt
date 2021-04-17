package com.caresomebody.test.submisi2fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.adapter.FavoriteAdapter
import com.caresomebody.test.submisi2fundamental.database.UserDbHelper
import com.caresomebody.test.submisi2fundamental.databinding.ActivityFavoriteUserBinding
import com.caresomebody.test.submisi2fundamental.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUser : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGit.setHasFixedSize(true)
        showRecyclerList()
        //loadUserAsync()
//        if (savedInstanceState == null) {
//            loadUserAsync()
//        } else {
//            val list = savedInstanceState.getParcelableArrayList<FavoriteModel>(EXTRA_STATE)
//            if (list != null) {
//                adapter.listUser = list
//            }
//        }

    }

    private fun showRecyclerList(){
        binding.rvGit.layoutManager = LinearLayoutManager(this)

        adapter = FavoriteAdapter()
        adapter.notifyDataSetChanged()
        binding.rvGit.adapter = adapter
    }

    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.VISIBLE
            val userHelper = UserDbHelper.getInstance(applicationContext)
            userHelper.open()
            val deferredUser = async(Dispatchers.IO){
                val cursor = userHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            userHelper.close()
            binding.progressBar.visibility = View.INVISIBLE
            val user = deferredUser.await()
            if (user.size > 0){
                adapter.listUser = user
            } else {
                adapter.listUser = ArrayList()
            }
        }
    }
}