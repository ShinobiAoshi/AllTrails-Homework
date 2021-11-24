package com.alltrails.lunch.ui

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import com.alltrails.lunch.R
import dagger.hilt.android.AndroidEntryPoint
import android.widget.EditText
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    // Clear focus from EditText when user clicks outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (view is EditText) {
                val outRect = Rect()
                view.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    view.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}