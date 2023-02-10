package com.otus.advancedviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import ru.otus.otusadvviews.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TextView(this).setText()

        /*findViewById<View>(R.id.helloWorld).setOnClickListener {
            findViewById<CustomViewChild>(R.id.customView).setValues(listOf(5, 3, 1, 4, 1, 0, 3))
        }

        findViewById<View>(R.id.helloWorld1).setOnClickListener {
            (findViewById<View>(R.id.child).layoutParams as CustomViewChild.OurLayoutParams).attachToTop = true
            findViewById<View>(R.id.child).requestLayout()
        }*/

        //LayoutParams
        //LinearLayout
        //FrameLayout.LayoutParams
    }
}
