package com.olympia.forRecyclerViews.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olympia.R
import com.olympia.databinding.HomeItemBinding
import com.olympia.forRecyclerViews.RVItem

class HomeRVAdapter(private val listener: Listener): RecyclerView.Adapter<HomeRVAdapter.HomeHolder> () {
    private val infArray = ArrayList<RVItem>()
    class HomeHolder(item: View): RecyclerView.ViewHolder(item) {
        private val itemBinding = HomeItemBinding.bind(item)
        fun bind(homeItem: RVItem, listener: Listener) = with(itemBinding){
            HomeImage.setImageResource(homeItem.imageId)
            HomeTextView.text = homeItem.title
            HomeItemCard.setOnClickListener {
                listener.onClick(homeItem)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        return HomeHolder(view)
    }
    override fun getItemCount(): Int {
        return infArray.size
    }
    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        holder.bind(infArray[position], listener)
    }
    interface Listener{
        fun onClick(homeItem: RVItem)
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addInf(homeItem: RVItem){
        infArray.add(homeItem)
        notifyDataSetChanged()
    }
}