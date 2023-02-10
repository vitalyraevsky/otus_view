package com.otus.advancedviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    companion object {
        const val TAG = "CustomView"
    }

    private val paint : Paint = Paint().apply {
        color = Color.GREEN
        strokeWidth = 10f
    }

    private val list = ArrayList<Int>()
    private var maxValue = 0
    
    init {
        setValues(listOf(5,3,1,4,1,0,3))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> Log.d(TAG, "onMeasure UNSPECIFIED")
            MeasureSpec.AT_MOST,
            MeasureSpec.EXACTLY -> {
                Log.d(TAG, "onMeasure EXACTLY")
                //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                setMeasuredDimension(widthSize, heightSize)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        Log.d(TAG, "onLayout")
    }

    override fun onDraw(canvas: Canvas?) {
        if (list.size == 0) return

        val widthPerView = width / list.size
        var currentX = 0
        val heightPerValue = height / maxValue

        for (item in list) {
            canvas?.drawRect(currentX.toFloat(), (height - heightPerValue * item).toFloat(), (currentX + widthPerView).toFloat(), height.toFloat(), paint)
            currentX += widthPerView
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