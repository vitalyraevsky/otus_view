package com.otus.advancedviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import ru.otus.otusadvviews.R

class CustomViewChild(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {
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
        val typeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomViewChild)
        paint.color = typeArray.getColor(R.styleable.CustomViewChild_customBarColor, Color.BLUE)

        typeArray.recycle()
        setValues(listOf(5,3,1,4,1,0,3))
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
                //super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                setMeasuredDimension(widthSize, heightSize)
                widthPerView = if (list.size > 0) measuredWidth / list.size else 0
            }
        }

        heightPerValue = if (maxValue > 0) measuredHeight / maxValue else 0

        if (childCount == 0) return

        val child = getChildAt(0)
        //measureChild(child, widthMeasureSpec, heightMeasureSpec)
        measureChild(child, MeasureSpec.makeMeasureSpec(widthPerView, MeasureSpec.EXACTLY), heightMeasureSpec)
    }

    // LinearLayout(parent) -> CustomViewGroup.onMeasure -> measure(child) -> Child.onMeasure -> setMeasuredDimension -> CustomViewGroup.onLayout

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) return

        val child = getChildAt(0)
        val layoutParams = child.layoutParams as OurLayoutParams

        for ((i, item) in list.withIndex()) {
            if (item == 0) {
                val position = widthPerView * i + widthPerView / 2 - child.measuredWidth / 2
                val positionVertical = if (layoutParams.attachToTop) 0 else height / 2
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
            //if (paint.color == Color.GRAY) paint.color = Color.MAGENTA else paint.color = Color.GRAY
        }

        super.dispatchDraw(canvas)
    }

    fun setValues(values : List<Int>) {
        Log.d(TAG, "setValues")

        list.clear()
        list.addAll(values)

        maxValue = list.maxOf { it }

        requestLayout()
        invalidate()
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return OurLayoutParams(context, attrs)
    }

    internal class OurLayoutParams(context: Context, attributeSet: AttributeSet) : LayoutParams(context,attributeSet) {

        var attachToTop: Boolean

        init {
            val typeArrayParams = context.obtainStyledAttributes(attributeSet, R.styleable.CustomViewChild_Layout)
            attachToTop = typeArrayParams.getBoolean(R.styleable.CustomViewChild_Layout_layout_attachToTop, false)
            typeArrayParams.recycle()
        }

    }
}