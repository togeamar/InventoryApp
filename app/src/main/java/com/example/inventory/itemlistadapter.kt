package com.example.inventory

import android.content.ClipData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.example.inventory.data.data
import com.example.inventory.data.getformattedprice
import com.example.inventory.databinding.ItemListItemBinding

class itemlistadapter(private val OnItemClicked:(data) -> Unit):
    ListAdapter<data,itemlistadapter.ItemViewHolder>(diffCallback) {
    //diffutill callback is used to not render all recyclerview when there is change in list.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener{
            OnItemClicked(current)
        }
        holder.bind(current)
    }
    class ItemViewHolder(private var binding: ItemListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: data) {
            binding.apply {
                itemName.text=data.name
                itemPrice.text=data.getformattedprice()
                itemQuantity.text=data.stock.toString()
            }

        }
    }
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<data>() {
            override fun areItemsTheSame(oldItem: data, newItem: data): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: data, newItem: data): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

}