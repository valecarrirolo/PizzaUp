package com.github.valecarrirolo.pizzaup

import android.content.Context
import android.util.TypedValue
import android.view.View

fun Double.formatPrice() = if (toInt().toDouble() == this) "${toInt()}€" else "${this}€"

fun Context.toPixelFromDip(value: Float) =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)

fun View.toPixelFromDip(value: Float) = context.toPixelFromDip(value)