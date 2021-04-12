package com.caresomebody.test.submisi2fundamental

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
                gitName.text = gitUser.name
                locUser.text = gitUser.location
                companyUser.text = gitUser.company

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
            it.context.startActivity<DetailActivity>("git" to listData[position])
        }
    }

    override fun getItemCount(): Int = listData.size

}