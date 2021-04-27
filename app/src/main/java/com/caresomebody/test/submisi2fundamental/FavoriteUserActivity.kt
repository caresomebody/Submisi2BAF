package com.caresomebody.test.submisi2fundamental

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.adapter.FavoriteAdapter
import com.caresomebody.test.submisi2fundamental.data.FavoriteData
import com.caresomebody.test.submisi2fundamental.database.GitUserContract.UserColumns.Companion.CONTENT_URI
import com.caresomebody.test.submisi2fundamental.database.GitUserHelper
import com.caresomebody.test.submisi2fundamental.databinding.ActivityFavoriteUserBinding
import com.caresomebody.test.submisi2fundamental.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteUserActivity : AppCompatActivity() {
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
        supportActionBar?.title = "Favorite User"

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadUserAsync()
            }
        }
        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadUserAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<FavoriteData>(EXTRA_STATE)
            if (list != null) {
                adapter.listUser = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listUser)
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
            val userHelper = GitUserHelper.getInstance(applicationContext)
            userHelper.open()
            val deferredUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
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