package com.github.valecarrirolo.pizzaup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        val binding = viewHolder.binding
        val context = binding.root.context
        binding.photo.load("https://raw.githubusercontent.com/nemsi85/dev-server/master/${item.photo}")
        binding.price.text = formatPrice(item.price)
        binding.description.text = item.ingredients.joinToString()
        binding.title.apply {
            text = item.name
            setTextColor(
                ContextCompat.getColor(
                    context,
                    if (item.num >= 1) R.color.deep_orange_800_dark else R.color.black
                )
            )
        }

        binding.numPizza.apply {
            text = item.num.toString()
            isVisible = item.num > 0
            setTextColor(
                ContextCompat.getColor(
                    context,
                    if (item.num >= 1) R.color.deep_orange_800_dark else R.color.yellow_50
                )
            )
        }

        binding.lessPizza.apply {
            isVisible = item.num > 0
            setOnClickListener {
                viewmodel.removePizza(item)
            }
        }

        binding.root.apply {
            cardElevation = if (item.num >= 1) context.toPixelFromDip(2f) else 0f
            setOnClickListener {
                viewmodel.addPizza(item)
            }
            setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    if (item.num >= 1) R.color.lime_200_light else R.color.yellow_50
                )
            )
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}

// ViewHolder
class MainViewHolder(val binding: ItemPizzaBinding) : RecyclerView.ViewHolder(binding.root)
