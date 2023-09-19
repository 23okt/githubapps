package com.example.appsgithub.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsgithub.databinding.ItemUserBinding
import com.example.appsgithub.response.GetItem
import com.example.appsgithub.ui.main.ProfileActivity

class MainAdapter(private val users: List<GetItem>): RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.binding.tvUser.text = user.login
        holder.binding.tvlink.text = user.htmlUrl
        Glide.with(holder.itemView.context)
            .load(user.avatarUrl)
            .into(holder.binding.ivUser)
        holder.itemView.setOnClickListener{
            val intentProfile = Intent(holder.itemView.context, ProfileActivity::class.java)
            intentProfile.putExtra(ProfileActivity.USER, user.login)
            holder.itemView.context.startActivity(intentProfile)
            }
        }
    }