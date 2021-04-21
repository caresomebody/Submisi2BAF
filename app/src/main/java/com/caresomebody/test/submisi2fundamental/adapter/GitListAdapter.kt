package com.caresomebody.test.submisi2fundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.caresomebody.test.submisi2fundamental.DetailActivity
import com.caresomebody.test.submisi2fundamental.data.GitUser
import com.caresomebody.test.submisi2fundamental.databinding.ItemRowBinding
import org.jetbrains.anko.startActivity

class GitListAdapter : RecyclerView.Adapter<GitListAdapter.ListViewHolder>() {
    private val listData = ArrayList<GitUser>()

    fun setData(items: ArrayList<GitUser>){
        listData.clear()
        listData.addAll(items)
        notifyDataSetChanged()
    }

    class ListViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gitUser: GitUser){
            with(binding){
                Glide.with(itemView.context)
                        .load(gitUser.avatar)
                        .apply(RequestOptions())
                        .into(avatarGit)
                gitUserName.text = gitUser.username
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.itemView.setOnClickListener{
            it.context.startActivity<DetailActivity>("git" to listData[position].username)
        }
    }

    override fun getItemCount(): Int = listData.size

}