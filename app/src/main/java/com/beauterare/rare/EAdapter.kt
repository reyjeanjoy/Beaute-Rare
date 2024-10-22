package com.beauterare.rare

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class EAdapter(private val context: Context, private val items: List<EMakeupItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.evening_list_items, parent, false)
        val item = getItem(position) as EMakeupItem

        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.textViewTitle)
        val priceRangeTextView: TextView = view.findViewById(R.id.textViewPriceRange)
        val bookButton: Button = view.findViewById(R.id.buttonBook)


        imageView.setImageResource(item.imageResId)
        imageView.contentDescription = item.title
        titleTextView.text = item.title
        priceRangeTextView.text = item.priceRange

        bookButton.setOnClickListener {
            val intent = Intent(context, PortfolioActivity::class.java)
            context.startActivity(intent)
        }
        return view
    }
}
