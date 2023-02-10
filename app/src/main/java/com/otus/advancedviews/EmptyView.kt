package com.otus.advancedviews

import android.content.Context
import android.graphics.Canvas
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View

const val TAG = "EmptyView"

class EmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var stateInt = 0

    init {
        Log.d(TAG, "init")
    }

    override fun onFinishInflate() {
        Log.d(TAG, "onFinishInflate")
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow")
        stateInt++
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        Log.d(TAG, "onMeasure")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.d(TAG, "onSizeChanged")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG, "layout")
        super.layout(l, t, r, b)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        Log.d(TAG, "onLayout")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        Log.d(TAG, "dispatchDraw")
        super.dispatchDraw(canvas)
    }

    override fun draw(canvas: Canvas?) {
        Log.d(TAG, "draw $stateInt")
        super.draw(canvas)
    }

    override fun onDraw(canvas: Canvas?) {
        Log.d(TAG, "onDraw")
        super.onDraw(canvas)
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow")
        super.onDetachedFromWindow()
    }

    override fun onSaveInstanceState(): Parcelable? {
        Log.d(TAG, "onSaveInstanceState")
        return MySavedState(super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.d(TAG, "onRestoreInstanceState")
        if(state !is MySavedState) {
            return super.onRestoreInstanceState(state)
        }

        super.onRestoreInstanceState(state.superState)
        stateInt = state.savedInt
    }

    private inner class MySavedState : BaseSavedState {

        internal val savedInt: Int

        constructor(source: Parcelable?) : super(source) {
            savedInt = this@EmptyView.stateInt
        }

        constructor(`in`: Parcel) : super(`in`) {
            savedInt = `in`.readInt() ?: 0
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(savedInt)
        }

        @JvmField
        val CREATOR: Parcelable.Creator<MySavedState> = object : Parcelable.Creator<MySavedState> {
            override fun createFromParcel(`in`: Parcel): MySavedState {
                return MySavedState(`in`)
            }

            override fun newArray(size: Int): Array<MySavedState?> {
                return arrayOfNulls(size)
            }
        }
    }
}