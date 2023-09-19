package com.example.appsgithub.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.appsgithub.R
import com.example.appsgithub.adapter.PagerAdapter
import com.example.appsgithub.databinding.ActivityProfileBinding
import com.example.appsgithub.response.DetailResponse
import com.example.appsgithub.ui.viewmodel.ProfileViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    lateinit var profileViewModel: ProfileViewModel

    companion object{
        const val USER = "user"
        @StringRes
        private val TAB = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
    @SuppressLint("StringFormatMatches", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val user = intent.getStringExtra(USER)
        profileViewModel.getProfileUser(user.toString())

        val sectionsAdapter = PagerAdapter(this, user?: " ")

        val viewPager: ViewPager2 = binding.pager
        viewPager.adapter = sectionsAdapter
        val tabs: TabLayout = binding.tabLay


        profileViewModel.profileUser.observe(this){profileUser->
            showData(profileUser)

            TabLayoutMediator(tabs, viewPager){tabs,position->
                tabs.text = resources.getString(TAB[position])
            }.attach()
        }
        profileViewModel.isLoading.observe(this ){
           binding.progressBar.visibility = if(it) View.VISIBLE else View.GONE
        }

    }

    private fun showData(profileUser: DetailResponse) {
        with(binding){
            tvName.text = profileUser.login
            tvUsername.text = profileUser.name
            tvFollownumbers.text = profileUser.followers.toString()
            tvFollowinumbers.text = profileUser.following.toString()
            Glide.with(this@ProfileActivity)
                .load(profileUser.avatarUrl)
                .into(ivProfile)
        }
    }
}