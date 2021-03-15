package com.github.valecarrirolo.pizzaup

import android.content.Context
import android.util.TypedValue
import android.view.View

fun formatPrice(price: Double): String {
    return if (price.toInt().toDouble() == price) {
        "${price.toInt()}€"
    } else {
        "${price}€"
    }
}

fun Context.toPixelFromDip(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
fun View.toPixelFromDip(value: Float) = context.toPixelFromDip(value)