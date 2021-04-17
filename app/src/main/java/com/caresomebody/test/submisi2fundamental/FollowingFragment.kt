package com.caresomebody.test.submisi2fundamental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.adapter.GitListAdapter
import com.caresomebody.test.submisi2fundamental.databinding.FragmentFollowingBinding
import com.caresomebody.test.submisi2fundamental.viewModel.DetailViewModel

class FollowingFragment : Fragment() {

    private lateinit var followingViewModel : DetailViewModel
    private lateinit var adapter: GitListAdapter
    private lateinit var binding: FragmentFollowingBinding

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showLoading(true)
        super.onViewCreated(view, savedInstanceState)
        binding.rvGit.setHasFixedSize(true)
        showRecyclerList()
        val username = arguments?.getString(ARG_USERNAME)
        followingViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        followingViewModel.setFollowing(username)
        followingViewModel.getFollowing().observe(viewLifecycleOwner, {listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList(){
        binding.rvGit.layoutManager = LinearLayoutManager(activity)
        adapter = GitListAdapter()
        adapter.notifyDataSetChanged()
        binding.rvGit.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.bringToFront()
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}