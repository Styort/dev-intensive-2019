package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


fun Activity.hideKeyboard(){
    val inputManager:InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.SHOW_FORCED)
}

fun TextView.onClickKeyboardDoneButton(funExecute: () -> Unit) {
    this.setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                funExecute.invoke()
                true
            }
            else -> false
        }
    }
}

fun Activity.isKeyboardOpen(): Boolean{
    /* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
    val heightKeyboardThreshold = 128;

    val r = Rect()
    //r will be populated with the coordinates of your view that area still visible.
    this.window.decorView.getWindowVisibleDisplayFrame(r)

    val dm = this.window.decorView.resources.displayMetrics
    /* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
    val heightDiff = this.window.decorView.bottom - r.bottom
    /* Threshold size: dp to pixels, multiply with display density */

    return heightDiff > heightKeyboardThreshold * dm.density
}

fun Activity.isKeyboardClosed(): Boolean{
    return !isKeyboardOpen()
}