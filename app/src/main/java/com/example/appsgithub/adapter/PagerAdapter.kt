package com.example.appsgithub.adapter


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.appsgithub.ui.main.FollowersFragment
import com.example.appsgithub.ui.main.FollowingFragment
import com.example.appsgithub.ui.main.ProfileActivity

class PagerAdapter(activity: AppCompatActivity, private val user: String):FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString(ProfileActivity.USER, user)

        val followersFragment = FollowersFragment()
        followersFragment.arguments = bundle

        val followingFragment = FollowingFragment()
        followingFragment.arguments = bundle

        return when(position){
            0-> followersFragment
            1-> followingFragment
                else  -> throw IllegalArgumentException("Invalid Position: $position")
        }
    }
}