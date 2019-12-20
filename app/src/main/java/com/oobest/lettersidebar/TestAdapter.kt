package com.oobest.lettersidebar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class TestAdapter(private val dataList: List<TestLetter>) :
    RecyclerView.Adapter<TestAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataList[position]
        holder.itemView.apply {
            letter_text.text = item.getLetterString()
            if (position == 0) {
                letter_text.visibility = View.VISIBLE
            } else {
                val lastItem = dataList[position - 1]
                if (lastItem.getLetterString() != item.getLetterString()) {
                    letter_text.visibility = View.VISIBLE
                } else {
                    letter_text.visibility = View.INVISIBLE
                }
            }
            text_view.text = item.name
        }
    }
}