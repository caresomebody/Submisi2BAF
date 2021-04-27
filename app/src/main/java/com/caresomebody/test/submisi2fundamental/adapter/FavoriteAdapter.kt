package com.caresomebody.test.submisi2fundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caresomebody.test.submisi2fundamental.DetailActivity
import com.caresomebody.test.submisi2fundamental.data.FavoriteData
import com.caresomebody.test.submisi2fundamental.databinding.ItemRowBinding
import org.jetbrains.anko.startActivity

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.UserViewHolder>() {
    private lateinit var deleteListener: ((FavoriteData, Int) -> Unit)
    var listUser = ArrayList<FavoriteData>()
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
        holder.itemView.setOnClickListener{
            it.context.startActivity<DetailActivity>("git" to listUser[position].username)
        }
    }

    override fun getItemCount(): Int = this.listUser.size

    class UserViewHolder(private val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(gitUser: FavoriteData){
            with(binding){
                Glide.with(itemView.context)
                        .load(gitUser.avatar)
                        .apply(RequestOptions())
                        .into(avatarGit)
                gitUserName.text = gitUser.username
            }
        }
    }

    fun setOnDeleteListener(listener: (FavoriteData, Int) -> Unit) {
        deleteListener = listener
    }
}