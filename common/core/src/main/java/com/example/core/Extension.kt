package com.example.core

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.core.utils.doOnDestroy
import com.example.core.utils.showSnack
import com.example.data.device.SensorType
import com.github.mikephil.charting.components.AxisBase
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Fragment.snackBar(text: String) {
    requireActivity().window.decorView.findViewById<View>(android.R.id.content).showSnack(text)
}

private val mainHandler = Handler(Looper.getMainLooper())
fun <B : ViewBinding> Fragment.fragmentViewBinding(
    bindingCreator: (View) -> B,
) = object : ReadOnlyProperty<Fragment, B> {
    private var binding: B? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): B {
        //view может быть null, если обращение к binding в onDestroyView
        this.binding
            ?.takeIf { view == null || it.root == view }
            ?.let { return it }

        val newBinding = bindingCreator(requireView())
        binding = newBinding
        viewLifecycleOwner.lifecycle.doOnDestroy {
            mainHandler.post {
                if (binding === newBinding) {
                    binding = null
                }
            }
        }

        return newBinding
    }
}

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density + 0.5f

fun AxisBase.setupEnvironments(font: Typeface?, textSize: Float, context: Context) {
    this.apply {
        gridColor = ContextCompat.getColor(context, R.color.gray1)
        enableGridDashedLine(10f, 10f, 0f)
        typeface = font
        this.textSize = textSize
        textColor = ContextCompat.getColor(context, R.color.gray1)
    }
}

fun String.isIpAddress(): Boolean {
    return ("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
            + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
            + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
            + "|[1-9][0-9]|[0-9]))").toRegex().matches(this)
}

fun createCenter(view: View, cancelable: Boolean = true): Dialog {
    val builder = AlertDialog.Builder(view.context)
    builder.setCancelable(cancelable)
    builder.setView(view)
    val dialog: Dialog = builder.create()
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    return dialog
}

fun Int.setPickerNumber(extremeValue: Int): Int {
    return if (this == -1) {
        extremeValue
    } else {
        this
    }
}

fun getSenorType(id: Int) = when (id) {
    SensorType.TemperatureSensor.type -> SensorType.TemperatureSensor
    SensorType.HumiditySensor.type -> SensorType.HumiditySensor
    SensorType.PressureSensor.type -> SensorType.PressureSensor
    SensorType.Conditioner.type -> SensorType.Conditioner
    SensorType.Humidifier.type -> SensorType.Humidifier
    else -> SensorType.Unknown
}

fun Int.toTime(): String {
    return this.toString().padStart(2, '0')
}

