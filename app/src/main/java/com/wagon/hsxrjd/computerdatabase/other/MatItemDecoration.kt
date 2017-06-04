package com.wagon.hsxrjd.computerdatabase.other

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by hsxrjd on 01.06.17.
 */
class MatItemDecoration(val mDivider: Drawable) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent?.getChildAdapterPosition(view) == 0) return
        when ((parent?.layoutManager as LinearLayoutManager).orientation) {
            LinearLayoutManager.HORIZONTAL -> {
                outRect?.left = mDivider.intrinsicWidth
            }
            LinearLayoutManager.VERTICAL -> {
                outRect?.left = mDivider.intrinsicHeight
            }
        }
    }

    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDraw(c, parent, state)

        when ((parent?.layoutManager as LinearLayoutManager).orientation) {
            LinearLayoutManager.HORIZONTAL -> {
                c?.let { drawHorizontalDividers(c, parent) }
            }
            LinearLayoutManager.VERTICAL -> {
                c?.let { drawVerticalDividers(c, parent) }
            }
        }
    }

    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentTop = parent.paddingTop
        val parentBottom = parent.height - parent.paddingBottom

        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentLeft = child.right + params.rightMargin
            val parentRight = parentLeft + mDivider.intrinsicWidth

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            mDivider.draw(canvas)
        }
    }

    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft
        val parentRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0..childCount - 2) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentTop = child.bottom + params.bottomMargin
            val parentBottom = parentTop + mDivider.intrinsicHeight

            mDivider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            mDivider.draw(canvas)
        }
    }
}