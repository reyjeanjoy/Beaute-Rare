package com.beauterare.rare

import ChooseArtistItem
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ChooseArtistAdapter(
    private val context: Context,
    private val items: List<ChooseArtistItem>
) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.choose_list_item, parent, false)
        val holder = ViewHolder(view)

        val item = getItem(position) as ChooseArtistItem
        holder.bind(item)

        holder.buttonViewPortfolio.setOnClickListener {
            context.startActivity(Intent(context, PortfolioActivity::class.java))
        }

        holder.buttonBook.setOnClickListener {
            context.startActivity(Intent(context, FormActivity::class.java))
        }

        return view
    }

    private class ViewHolder(view: View) {
        val imageView: ImageView = view.findViewById(R.id.imageViewArtist1)
        val textViewName: TextView = view.findViewById(R.id.textViewName1)
        val textViewLocation: TextView = view.findViewById(R.id.textViewLocation1)
        val buttonViewPortfolio: Button = view.findViewById(R.id.buttonViewPortfolio1)
        val buttonBook: Button = view.findViewById(R.id.buttonBook1)

        fun bind(item: ChooseArtistItem) {
            imageView.setImageResource(item.imageResId)
            textViewName.text = item.name
            textViewLocation.text = item.location
        }
    }
}