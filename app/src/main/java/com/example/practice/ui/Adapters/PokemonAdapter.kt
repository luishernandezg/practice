package com.example.practice.ui.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice.databinding.ItemPokemonBinding
import com.example.practice.model.PokemonItem

/**
 * Created by Luis hernandez on 11/8/2021.
 * Email: luis.guaicaipuro.h.a@gmail.com
 */
class PokemonAdapter(private var items: List<PokemonItem>, private var listener : PokemonAdapterClickListener): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<PokemonItem>){
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PokemonItem) {
            binding.pokemon = item
            /*val name = item.ComingGuest.name!!.split(" ")[0]
            val lastname = item.ComingGuest.name.split(" ")[1]
            val character: String? = name.take(1)
            val character2: String? = lastname.take(1)
            val drawable : TextDrawable = TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRound(character+character2, Color.rgb(120, 211, 187))
            binding.guestImage1InItemInvitation.setImageDrawable(drawable)
            binding.shareActionInInvitationItem .setOnClickListener {
                listener.onItemClick(item)
            }*/
            binding.executePendingBindings()
        }
    }
}