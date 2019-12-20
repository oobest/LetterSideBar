package com.oobest.lettersidebar.widget

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * 列表按字母排序，且根据滑动LetterSideBar,RcyclerView自动滚动到相应位置
 */


interface ILetter {
    fun getLetterString(): String
}


fun RecyclerView.bindLetterSideBar(
    letterSideBar: LetterSideBar,
    fetchLetterList: () -> List<ILetter>?,
    touchLetterCallback: ((String?) -> Unit)? = null
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val letterList = fetchLetterList.invoke()
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
            val endVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
            val startLetter = letterList?.get(firstVisibleItemPosition)?.getLetterString()
            val endLetter =
                if (endVisibleItemPosition == recyclerView.adapter?.itemCount ?: 0 - 1) {
                    "Z"
                } else {
                    letterList?.get(endVisibleItemPosition)?.getLetterString()
                }
            if (startLetter != null && endLetter != null) {
                letterSideBar.setShowLetter(startLetter, endLetter)
            }
        }
    })

    letterSideBar.setOnTouchingLetterChangedListener { letter ->
        touchLetterCallback?.invoke(letter)
        letter?.also {
            val letterList = fetchLetterList.invoke()
            val position = computeScrollPosition(letterList, it)
            if (position > -1) {
                val smoothScroller = TopSmoothScroller(this.context).apply {
                    targetPosition = position
                }
                layoutManager?.startSmoothScroll(smoothScroller)
            }
        }
    }
}

private fun computeScrollPosition(letterList: List<ILetter>?, letter: String): Int {
    if (letterList.isNullOrEmpty()) {
        return -1
    } else {
        var start: Int = -1
        var end: Int = -1
        for ((index, item) in letterList.withIndex()) {
            val temp = item.getLetterString()
            if (temp == letter) {
                return index
            } else if (temp < letter) {
                start = index
            } else if (temp > letter && end == -1) {
                end = index
            }

            if (start != -1 && end != -1) {
                break
            }
        }

        return if (start != -1 && end == -1) {
            letterList.size - 1
        } else if (end != -1) {
            end - 1
        } else {
            -1
        }
    }
}