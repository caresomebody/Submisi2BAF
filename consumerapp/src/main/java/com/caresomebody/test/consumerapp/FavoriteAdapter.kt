package com.caresomebody.test.consumerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caresomebody.test.consumerapp.FavoriteModel
import com.caresomebody.test.consumerapp.databinding.ItemRowBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {

    var listUser = ArrayList<FavoriteModel>()
    set(listUser){
        if (listUser.size > 0){
            this.listUser.clear()
        }
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = this.listUser.size

    class UserViewHolder(private val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(gitUser: FavoriteModel){
            with(binding){
                Glide.with(itemView.context)
                        .load(gitUser.avatar)
                        .apply(RequestOptions())
                        .into(avatarGit)
                gitUserName.text = gitUser.username
            }
        }
    }
}