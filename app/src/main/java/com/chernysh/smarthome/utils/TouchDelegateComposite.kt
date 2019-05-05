package com.chernysh.smarthome.utils

import android.graphics.Rect
import android.view.MotionEvent
import android.view.TouchDelegate
import android.view.View

import java.util.ArrayList

class TouchDelegateComposite(view: View) : TouchDelegate(Rect(), view) {

    private val delegates = ArrayList<TouchDelegate>()

    fun addDelegate(delegate: TouchDelegate?) {
        if (delegate != null) {
            delegates.add(delegate)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var res = false
        val x = event.x
        val y = event.y
        for (delegate in delegates) {
            event.setLocation(x, y)
            res = delegate.onTouchEvent(event) || res
        }
        return res
    }
}

fun View.expandClickArea(parent: View, top: Int = 0, bottom: Int = 0, left: Int = 0, right: Int = 0) {
    parent.post {
        this.isEnabled = true
        val delegateArea = getDelegatedArea(right, bottom, top, left)

        if (View::class.java.isInstance(this.parent)) {
            processTouchDelegate(delegateArea)
        }
    }
}

private fun View.getDelegatedArea(right: Int, bottom: Int, top: Int, left: Int): Rect {
    val delegateArea = Rect()
    this.getHitRect(delegateArea)

    return delegateArea.apply {
        this.right += right
        this.bottom += bottom
        this.top -= top
        this.left -= left
    }
}

private fun View.processTouchDelegate(delegateArea: Rect) {
    val parentTouchDelegate = (this.parent as View).touchDelegate
    val newDelegate = TouchDelegate(delegateArea, this)

    (this.parent as View).touchDelegate = if (parentTouchDelegate != null) {
        getParentOrCompositeTouchDelegate(parentTouchDelegate, newDelegate)
    } else {
        newDelegate
    }
}

private fun View.getParentOrCompositeTouchDelegate(parentTouchDelegate: TouchDelegate?, newDelegate: TouchDelegate) =
        if (TouchDelegateComposite::class.java.isInstance(parentTouchDelegate)) {
            (parentTouchDelegate as TouchDelegateComposite).apply { addDelegate(newDelegate) }
        } else {
            TouchDelegateComposite(this).apply {
                addDelegate(parentTouchDelegate)
                addDelegate(newDelegate)
            }
        }