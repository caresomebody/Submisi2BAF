package com.caresomebody.test.submisi2fundamental.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.caresomebody.test.submisi2fundamental.FollowersFragment
import com.caresomebody.test.submisi2fundamental.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String?): FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = FollowingFragment.newInstance(username)
            1 -> fragment = FollowersFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}