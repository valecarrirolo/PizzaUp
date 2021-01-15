package com.github.valecarrirolo.pizzaup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.valecarrirolo.pizzaup.databinding.ItemPizzaBinding


// Adapter
class MainAdapter() : RecyclerView.Adapter<MainViewHolder>() {

    var dataSet: List<PizzaDetail> = emptyList()

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MainViewHolder {

        // ViewBinding
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemPizzaBinding.inflate(layoutInflater, viewGroup, false)
        return MainViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: MainViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.binding.title.text = item.name
        viewHolder.binding.price.text = format(item.price)
        viewHolder.binding.description.text = item.ingredients.joinToString()
    }

    fun format(price: Double): String {
        return if (price.toInt().toDouble() == price) {
            "${price.toInt()}€"
        } else {
            "${price}€"
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}

// ViewHolder
class MainViewHolder(val binding: ItemPizzaBinding) : RecyclerView.ViewHolder(binding.root)
