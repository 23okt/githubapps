package com.example.appsgithub.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsgithub.R
import com.example.appsgithub.adapter.UserFollowAdapter
import com.example.appsgithub.databinding.FragmentFollowingBinding
import com.example.appsgithub.ui.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private lateinit var _binding: FragmentFollowingBinding
    private val binding get() = _binding!!
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val profileActivity = activity as ProfileActivity
        followingViewModel = ViewModelProvider(
            profileActivity,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]
        profileActivity.profileViewModel.profileUser.observe(viewLifecycleOwner) {
            if (it.following <= 0) {
                binding.tvFollowing.text = getString(R.string.follow_nofound)
            }
        }

        val user = arguments?.getString(ProfileActivity.USER)
        user?.let {
            followingViewModel.getFollowing(it)
        }

        followingViewModel.following.observe(viewLifecycleOwner) {
            binding.rvFollowing.apply {
                val adapter = UserFollowAdapter(it)
                binding.rvFollowing.adapter = adapter
                binding.rvFollowing.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            binding.tvFollowing.visibility = if (it.isNullOrEmpty()) View.VISIBLE else View.INVISIBLE
        }
        followingViewModel.isLoading.observe(viewLifecycleOwner){
            binding.barFollowing.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        return binding.root
    }
}