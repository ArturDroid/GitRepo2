package com.ardroid.gitrepo.temp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ardroid.gitrepo.R
import kotlinx.android.synthetic.main.repos_item.view.*

class ReposAdapter(private val reposList: List<ReposItem>) :
    RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.repos_item,
            parent, false
        )
        return ReposViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val currentItem = reposList[position]
        holder.textView1.text = currentItem.username
        holder.textView2.text = currentItem.lang
        holder.textView3.text = currentItem.Id


    }

    override fun getItemCount() = reposList.size

    class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textView1: TextView = itemView.text_view_repos_name
        val textView2: TextView = itemView.text_view_lang
        val textView3: TextView = itemView.text_view_id
    }
}