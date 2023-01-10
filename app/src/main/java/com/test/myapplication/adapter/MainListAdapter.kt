package com.test.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.test.myapplication.R
import com.test.myapplication.databinding.AdapterListBinding
import com.test.myapplication.listener.ListClickListener
import com.test.myapplication.model.DataList

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.MainViewHolder>() {
    var listData = mutableListOf<DataList>()
    var listener: ListClickListener? = null

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

        Glide.with(holder.itemView.context).load(data.download_url).
        apply( RequestOptions().override(200, 200)).
        placeholder(R.mipmap.ic_launcher_round).
        into(holder.binding.ivImage)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun setOnClickListener(listener: ListClickListener){
        this.listener = listener
    }

    inner class MainViewHolder(val binding: AdapterListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener { this}
        }

        override fun onClick(p0: View?) {
            listener?.onListClick(listData[adapterPosition])
        }

    }
}

