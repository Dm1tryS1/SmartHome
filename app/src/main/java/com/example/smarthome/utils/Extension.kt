package com.example.smarthome.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.github.mikephil.charting.components.AxisBase

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Fragment.snackBar(text: String) {
    requireActivity().window.decorView.findViewById<View>(android.R.id.content).showSnack(text)
}

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f

fun AxisBase.setupEnvironments(font: Typeface?, textSize: Float, context: Context) {
    this.apply {
        gridColor = ContextCompat.getColor(context, R.color.black)
        enableGridDashedLine(10f, 10f, 0f)
        typeface = font
        this.textSize = textSize
        textColor = ContextCompat.getColor(context, R.color.black)
    }
}