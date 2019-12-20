package com.oobest.lettersidebar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oobest.lettersidebar.widget.bindLetterSideBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val type = object : TypeToken<List<TestLetter>>() {}.type
        val list =
            Gson().fromJson<List<TestLetter>>(testDate, type).sortedBy { it.pinyin }


        val adapter = TestAdapter(list)
        recyclerView.adapter = adapter

        recyclerView.bindLetterSideBar(
            letterSideBar = letter_side_bar,
            fetchLetterList = { list },
            touchLetterCallback = {
                choose_letter_text.visibility = if (it.isNullOrEmpty()) View.GONE else View.VISIBLE
                choose_letter_text.text = it ?: ""
            })
    }
}
