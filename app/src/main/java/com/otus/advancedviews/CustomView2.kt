package com.otus.advancedviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView2(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    companion object {
        const val TAG = "CustomView"
    }

    private val paint = Paint().apply {
        color = Color.GRAY
        strokeWidth = 10f
    }

    private var defaultWidth = 450

    private val list = ArrayList<Int>()
    private var maxValue : Int = 0
    private var widthPerView = 0
    private var heightPerValue = 0

    init {
        setValues(listOf(5,3,1,4,1,0,3))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        defaultWidth = widthSize / 3

        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> {
                setMeasuredDimension(if (list.size == 0) 0 else defaultWidth * list.size, heightSize)
                widthPerView = if (list.size == 0) 0 else defaultWidth

            }
            MeasureSpec.AT_MOST,
            MeasureSpec.EXACTLY -> {
                Log.d(TAG, "onMeasure EXACTLY")
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                widthPerView = if (list.size > 0) measuredWidth / list.size else 0
            }
        }

        heightPerValue = if (maxValue > 0) measuredHeight / maxValue else 0
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        Log.d(TAG, "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        if (list.size == 0) return

        var currentX = 0

        for (item in list) {
            canvas?.drawRect(currentX.toFloat(), (height - heightPerValue * item).toFloat(), (currentX + widthPerView).toFloat(), height.toFloat(), paint)
            currentX += widthPerView
            if (paint.color == Color.GRAY) paint.color = Color.MAGENTA else paint.color = Color.GRAY
        }

        Log.d(TAG, "onDraw")
    }

    fun setValues(values : List<Int>) {
        Log.d(TAG, "setValues")

        list.clear()
        list.addAll(values)

        maxValue = list.maxOf { it }

        requestLayout()
        invalidate()
    }
}