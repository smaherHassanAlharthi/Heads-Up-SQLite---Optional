package com.example.headsup.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.headsup.database.Celebrity
import com.example.headsup.databinding.ItemRowBinding

class RVAdapter(private var celebrities: ArrayList<Celebrity>): RecyclerView.Adapter<RVAdapter.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val celebrity = celebrities[position]

        holder.binding.apply {
            tvCelebrity.text = "${celebrity.name} - ${celebrity.taboo1} - ${celebrity.taboo2} - ${celebrity.taboo3}"
        }
    }

    override fun getItemCount() = celebrities.size

    fun update(celebrities: ArrayList<Celebrity>){
        this.celebrities = celebrities
        notifyDataSetChanged()
    }
}