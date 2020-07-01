package com.ardroid.gitrepo.temp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardroid.gitrepo.R
import com.ardroid.gitrepo.dataSources.data.DataContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.example_item.view.*

class ExampleAdapter(
    private val exampleList: List<ExampleItem>,
    private val recyclerClickListener: RecyclerViewClick
) : RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder>() {

    constructor(
        exampleList: List<ExampleItem>,
        recyclerClickListener: (view: View, item: ExampleItem, position: Int) -> Unit
    ) : this(exampleList, object : RecyclerViewClick {
        override fun onClickOnItemClick(view: View, item: ExampleItem, position: Int) {
            recyclerClickListener.invoke(view, item, position)
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.example_item,
            parent,
            false
        )
        return ExampleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind(exampleList[position], position)

    }

    override fun getItemCount() = exampleList.size

    inner class ExampleViewHolder(private val holder: View) : RecyclerView.ViewHolder(holder) {
        fun bind(item: ExampleItem, position: Int) {
           Glide.with(DataContext.getContext()).asBitmap()
               .load(item.imageUrl).apply(RequestOptions().override(100,100))
               .apply(RequestOptions().placeholder(R.drawable.ic_person_placeholder))
               .into(holder.image_view)
            holder.text_view_1.text = item.username
            holder.text_view_2.text = item.text2
            holder.setOnClickListener {
                recyclerClickListener.onClickOnItemClick(it, item, position)
            }

//            if (position == 0) {
//                itemView.text_view_1.setBackgroundColor(Color.RED)
//            }
        }

    }
}

