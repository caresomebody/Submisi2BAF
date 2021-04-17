package com.caresomebody.test.submisi2fundamental

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.caresomebody.test.submisi2fundamental.adapter.GitListAdapter
import com.caresomebody.test.submisi2fundamental.databinding.FragmentFollowersBinding
import com.caresomebody.test.submisi2fundamental.viewModel.DetailViewModel

class FollowersFragment : Fragment() {

    private lateinit var followerViewModel: DetailViewModel
    private lateinit var adapter: GitListAdapter
    private lateinit var binding: FragmentFollowersBinding

    companion object {
        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment = FollowersFragment()
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
        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showLoading(true)
        super.onViewCreated(view, savedInstanceState)
        binding.rvGit.setHasFixedSize(true)
        showRecyclerList()
        val username = arguments?.getString(ARG_USERNAME)
        followerViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        followerViewModel.setFollowers(username)
        followerViewModel.getFollowers().observe(viewLifecycleOwner, {listFollowers ->
            if (listFollowers != null) {
                adapter.setData(listFollowers)
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
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}