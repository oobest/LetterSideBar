package com.oobest.lettersidebar.widget

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class TopSmoothScroller(ctx: Context) : LinearSmoothScroller(ctx) {

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }
}