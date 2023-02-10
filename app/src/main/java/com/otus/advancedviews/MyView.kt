package com.otus.advancedviews

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import ru.otus.otusadvviews.R

class MyView @JvmOverloads constructor(
    context: Context,
    attrs:  AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.my_text) }

    init {
        inflate(context, R.layout.layout_my, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        textView.setText("new text")
    }
}