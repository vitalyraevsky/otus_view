package com.otus.advancedviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import ru.otus.otusadvviews.R

class CustomViewChild234(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {
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
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomViewChild123)
        try {
            paint.color = typedArray.getColor(R.styleable.CustomViewChild123_barColor, Color.GRAY)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            MeasureSpec.UNSPECIFIED -> {
                Log.d(TAG, "onMeasure UNSPECIFIED")
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

        if (childCount == 0) return
        
        val child = getChildAt(0)
        measureChild(child, widthMeasureSpec, heightMeasureSpec)
        //measureChild(child, MeasureSpec.makeMeasureSpec(widthPerView, MeasureSpec.EXACTLY), heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "onLayout $childCount")
        Log.d(TAG, "onLayout $l $t $r $b")

        if (childCount == 0) return
        val child : View = getChildAt(0)
        Log.d(TAG, "onLayout ${child.measuredWidth}")
        Log.d(TAG, "onLayout ${widthPerView}")

        for ((i, item) in list.withIndex()) {
            if (item == 0) {
                val lp = child.layoutParams as CustomLayoutParams
                val position = widthPerView * i + widthPerView / 2 - child.measuredWidth / 2
                val positionVertical = height / 2
                //val positionVertical = if (lp.stickToTop) 0 else height / 2
                Log.d("lpsStcik", lp.stickToTop.toString())
                child.layout(position, positionVertical, position + child.measuredWidth, positionVertical + child.measuredHeight)
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {

        if (list.size == 0) return

        var currentX = 0

        for (item in list) {
            canvas?.drawRect(currentX.toFloat(), (height - heightPerValue * item).toFloat(), (currentX + widthPerView).toFloat(), height.toFloat(), paint)
            currentX += widthPerView
            if (paint.color == Color.GRAY) paint.color = Color.MAGENTA else paint.color = Color.GRAY
        }

        Log.d(TAG, "dispatchDraw")
        super.dispatchDraw(canvas)
    }

    fun setValues(values : List<Int>) {
        Log.d(TAG, "setValues")

        list.clear()
        list.addAll(values)

        maxValue = list.maxOf { it }
        //widthPerView = if (list.size > 0) measuredWidth / list.size else 0
        //heightPerValue = if (maxValue > 0) measuredHeight / maxValue else 0

        requestLayout()
        invalidate()
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is CustomLayoutParams
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        Log.d("customview123", "generateLP 0")
        return CustomLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        Log.d("customview123", "generateLP 1")
        return CustomLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }



    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        Log.d("customview123", "generateLP 2")
        return CustomLayoutParams(p)
    }

    class CustomLayoutParams : MarginLayoutParams {
        constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
            readStyleParams(context, attributeSet)
        }

        constructor(layoutParams: ViewGroup.LayoutParams) : super(layoutParams)
        constructor(width: Int, height: Int) : super(width, height)

        public var stickToTop: Boolean = false

        private fun readStyleParams(context: Context, attributeSet: AttributeSet) {
            Log.d("customview123", "readStyleParams lp 0")
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomViewChild123_Layout)
            try {
                stickToTop = typedArray.getBoolean(R.styleable.CustomViewChild123_Layout_layout_stickToTop1, false)
            } finally {
                typedArray.recycle()
            }
        }
    }
}