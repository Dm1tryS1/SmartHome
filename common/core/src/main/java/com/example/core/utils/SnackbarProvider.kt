package com.example.core.utils

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import com.example.core.R
import com.example.core.dp
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(
    text: String,
    actionText: String? = null,
    actionClick: View.OnClickListener? = null,
    dismissed: ((Unit) -> Unit)? = null,
) {
    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)

    snackBar.view.apply {
        findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setTextAppearance(R.style.textButton)
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        val params = layoutParams as ViewGroup.MarginLayoutParams
        val margin = 8.dp

        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.setMargins(margin, margin, margin, 10.dp)

        layoutParams = params
        translationZ = 100F
        background = AppCompatResources.getDrawable(context, R.drawable.bg_snackbar)

        ViewCompat.setElevation(this, 2.dp.toFloat())
    }

    if (actionText != null) {
        snackBar.setAction(actionText, actionClick)
    }
    snackBar.setActionTextColor(context.getColor(R.color.blue))
    snackBar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onShown(transientBottomBar: Snackbar?) {
            super.onShown(transientBottomBar)
        }

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            dismissed?.invoke(Unit)
        }
    })

    snackBar.show()
}