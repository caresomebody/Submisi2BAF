package com.caresomebody.test.submisi2fundamental

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.databinding.ActivityMainBinding
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitListAdapter
    //private val list = ArrayList<GitUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvGit.setHasFixedSize(true)

        adapter = GitListAdapter()
        adapter.notifyDataSetChanged()
        //list.addAll(GitData.listData)
        recyclerView().adapter = adapter
        showRecyclerList()
    }

    private fun showRecyclerList(){
        binding.rvGit.layoutManager = LinearLayoutManager(this)
        //val listGitAdapter = GitListAdapter (list)
        //binding.rvGit.adapter = listGitAdapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}