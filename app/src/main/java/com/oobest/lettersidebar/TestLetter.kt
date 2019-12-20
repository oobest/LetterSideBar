package com.oobest.lettersidebar

import com.oobest.lettersidebar.widget.ILetter

data class TestLetter(
    val name: String,
    val pinyin: String
) : ILetter {
    override fun getLetterString(): String {
        return pinyin.substring(0, 1)
    }

}