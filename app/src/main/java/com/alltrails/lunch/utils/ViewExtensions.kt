package com.alltrails.lunch.utils

import android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
import android.widget.EditText

fun EditText.onSearch(callback: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener if (actionId == IME_ACTION_SEARCH) {
            callback.invoke()
            true
        } else {
            false
        }
    }
}