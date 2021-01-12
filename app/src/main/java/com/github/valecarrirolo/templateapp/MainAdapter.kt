package com.github.valecarrirolo.templateapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.valecarrirolo.templateapp.databinding.ItemBasicBinding

data class BasicItem(val title: String, val description: String)

// Adapter
class MainAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    var dataSet : List<Quote> = emptyList()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {

        // ViewBinding
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBasicBinding.inflate(layoutInflater, viewGroup, false)
        return MainViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.binding.title.text = item.author
        viewHolder.binding.description.text = item.text
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

// ViewHolder
class MainViewHolder(val binding: ItemBasicBinding) : RecyclerView.ViewHolder(binding.root)