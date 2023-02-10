package com.otus.advancedviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import ru.otus.otusadvviews.R

class CustomViewBaseline : FrameLayout {
    private val tag = "CustomViewBaseline"

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        Log.d(tag, "onFinishInflate")
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr : Int) : super(context, attributeSet, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        Log.d(tag, "constructor")
        LayoutInflater.from(context).inflate(R.layout.view_baseline, this, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        Log.d(tag, "onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        Log.d(tag, "onLayout")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(tag, "onAttachedToWindow")
    }

    override fun getBaseline(): Int {
        return findViewById<View>(R.id.image_view).measuredHeight
    }
}