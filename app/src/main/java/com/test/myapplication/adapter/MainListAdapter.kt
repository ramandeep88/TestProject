package com.test.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.myapplication.databinding.AdapterListBinding
import com.test.myapplication.model.DataList

class MainListAdapter : RecyclerView.Adapter<MainViewHolder>() {
    var listData = mutableListOf<DataList>()

    fun setList(data: MutableList<DataList>) {
        this.listData = data
        notifyDataSetChanged()
    }

    fun addList(data: MutableList<DataList>) {
        this.listData.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterListBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listData[position]
        holder.binding.tvTitle.text = data.author
        Glide.with(holder.itemView.context).load(data.download_url).into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

class MainViewHolder(val binding: AdapterListBinding) : RecyclerView.ViewHolder(binding.root) {
}