package com.example.appsgithub.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsgithub.R
import com.example.appsgithub.adapter.UserFollowersAdapter
import com.example.appsgithub.databinding.FragmentFollowersBinding
import com.example.appsgithub.ui.viewmodel.FollowersViewModel



class FollowersFragment : Fragment() {
    private lateinit var _binding: FragmentFollowersBinding
    private val binding get() = _binding!!
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        val profileActivity = activity as ProfileActivity
        followersViewModel = ViewModelProvider(
            profileActivity,
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]

        profileActivity.profileViewModel.profileUser.observe(viewLifecycleOwner) {
            if (it.following <= 0) {
                binding.tvFollow.text = getString(R.string.follower_nofound)
            }
        }

        val user = arguments?.getString(ProfileActivity.USER)
        user?.let {
            followersViewModel.getFollowUser(it)
        }

        followersViewModel.listFollowers.observe(viewLifecycleOwner) {
            binding.rvFollow.apply {
                val adapter = UserFollowersAdapter(it)
                binding.rvFollow.adapter = adapter
                binding.rvFollow.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            binding.tvFollow.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        }
        return binding.root
    }

}
