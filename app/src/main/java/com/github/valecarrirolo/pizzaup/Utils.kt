package com.github.valecarrirolo.pizzaup


fun formatPrice(price: Double): String {
    return if (price.toInt().toDouble() == price) {
        "${price.toInt()}€"
    } else {
        "${price}€"
    }
}