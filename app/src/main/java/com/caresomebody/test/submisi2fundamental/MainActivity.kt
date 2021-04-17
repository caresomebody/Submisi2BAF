package com.caresomebody.test.submisi2fundamental

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.adapter.GitListAdapter
import com.caresomebody.test.submisi2fundamental.databinding.ActivityMainBinding
import com.caresomebody.test.submisi2fundamental.viewModel.MainViewModel
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitListAdapter
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGit.setHasFixedSize(true)
        showRecyclerList()
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(this, { gitUser ->
            if(gitUser != null){
                adapter.setData(gitUser)
                showLoading(false)
            }
        })
        mainViewModel.getToastObserver().observe(this, {message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataFromApi(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        if (item.itemId == R.id.favorite_menu) {
            toast("berhasil diklik")
            val mIntent = Intent(this, FavoriteUser::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    fun getDataFromApi(username: String){
        if (username.isEmpty()) return
        showLoading(true)
        mainViewModel.setUser(username)
    }

    private fun showRecyclerList(){
        binding.rvGit.layoutManager = LinearLayoutManager(this)

        adapter = GitListAdapter()
        adapter.notifyDataSetChanged()

        binding.rvGit.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}