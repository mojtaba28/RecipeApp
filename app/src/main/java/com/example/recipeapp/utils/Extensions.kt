package com.example.recipeapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.todkars.shimmer.ShimmerRecyclerView
import java.util.regex.Pattern

fun isValidEmail(email: CharSequence): Boolean {
    var isValid = true
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(email)
    if (!matcher.matches()) {
        isValid = false
    }
    return isValid
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

 fun setupLoading(isShownLoading: Boolean, shimmer: ShimmerRecyclerView) {
    shimmer.apply {
        if (isShownLoading) showShimmer() else hideShimmer()
    }
}

fun Int.minToHour(): String {
    val time: String
    val hours: Int = this / 60
    val minutes: Int = this % 60
    time = if (hours > 0) "${hours}h:${minutes}min" else "${minutes}min"
    return time
}

fun TextView.setDynamicallyColor(color: Int) {
    //Start - Left = 0 || Top = 1 || End - Right = 2 || Bottom = 3
    this.compoundDrawables[1].setTint(ContextCompat.getColor(context, color))
    this.setTextColor(ContextCompat.getColor(context, color))
}