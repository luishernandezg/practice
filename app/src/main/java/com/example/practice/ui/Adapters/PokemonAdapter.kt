package com.example.practice.ui.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.databinding.ItemPokemonBinding
import com.example.practice.model.PokemonItem

/**
 * Created by Luis hernandez on 11/8/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
class PokemonAdapter(diffCallback: DiffUtil.ItemCallback<PokemonItem>) :
    PagingDataAdapter<PokemonItem, PokemonAdapter.ViewHolder>(diffCallback) {

//RecyclerView.Adapter<PokemonAdapter.ViewHolder>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

//    override fun getItemCount(): Int = items.size

    fun setItems(items: List<PokemonItem>){
//        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonItem?) {
            binding.pokemon = item
            binding.executePendingBindings()
        }
    }

    object PokemonComparator : DiffUtil.ItemCallback<PokemonItem>() {
        override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
            // url is unique.
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
            return oldItem == newItem
        }
    }
}