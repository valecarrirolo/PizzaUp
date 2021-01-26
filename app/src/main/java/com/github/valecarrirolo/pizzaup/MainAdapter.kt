package com.github.valecarrirolo.pizzaup

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.valecarrirolo.pizzaup.databinding.ItemPizzaBinding


// Adapter
class MainAdapter(val viewmodel: MainViewModel) : RecyclerView.Adapter<MainViewHolder>() {

    var dataSet: List<NumPizzaDetail> = emptyList()

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
        viewHolder.binding.photo.load("https://raw.githubusercontent.com/nemsi85/dev-server/master/${item.photo}")
        viewHolder.binding.price.text = format(item.price)
        viewHolder.binding.description.text = item.ingredients.joinToString()
        viewHolder.binding.numpizza.text = item.num.toString()
        viewHolder.binding.root.setOnClickListener {
            viewmodel.addPizza(item)
        }
        viewHolder.binding.root.setBackgroundColor(
            Color.parseColor(if (item.num >= 1) "#f3e5f5" else "#ffffff"))
        viewHolder.binding.title.setTextColor(
            Color.parseColor(if (item.num >= 1) "#FF3700B3" else "#ffffff"))
        viewHolder.binding.numpizza.setTextColor(
            Color.parseColor(if (item.num >= 1) "#FF3700B3" else "#ffffff"))

        viewHolder.binding.lesspizza.isVisible = item.num > 0
        viewHolder.binding.lesspizza.setOnClickListener {
            viewmodel.removePizza(item)
        }
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
