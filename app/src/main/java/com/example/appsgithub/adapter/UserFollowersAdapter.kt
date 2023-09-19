package com.example.appsgithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsgithub.databinding.ItemFollowersBinding
import com.example.appsgithub.response.FollowersResponseItem
import com.example.appsgithub.response.GetItem
import com.example.appsgithub.ui.main.ProfileActivity

class UserFollowersAdapter(private val users: List<FollowersResponseItem>): RecyclerView.Adapter<UserFollowersAdapter.ViewHolder>(){
    inner class ViewHolder(var binding: ItemFollowersBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvFollower.text = user.login
        holder.binding.tvLink.text = user.htmlUrl
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.ivFollowers)
        holder.binding.viewCard.setOnClickListener {
            val intentFollow = Intent(holder.itemView.context, ProfileActivity::class.java)
            intentFollow.putExtra(ProfileActivity.USER, user.login)
            holder.itemView.context.startActivity(intentFollow)
        }
    }
}