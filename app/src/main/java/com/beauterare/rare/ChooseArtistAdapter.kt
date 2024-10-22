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
import com.beauterare.rare.R

class ChooseArtistAdapter(private val context: Context, private val items: List<ChooseArtistItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.choose_list_item, parent, false)
            holder = ViewHolder()
            holder.imageView = view.findViewById(R.id.imageViewArtist1)
            holder.textViewName = view.findViewById(R.id.textViewName1)
            holder.textViewLocation = view.findViewById(R.id.textViewLocation1)
            holder.buttonViewPortfolio = view.findViewById(R.id.buttonViewPortfolio1)
            holder.buttonBook = view.findViewById(R.id.buttonBook1)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val item = getItem(position) as ChooseArtistItem
        holder.imageView.setImageResource(item.imageResId)
        holder.textViewName.text = item.name
        holder.textViewLocation.text = item.location

        holder.buttonViewPortfolio.setOnClickListener {
            val intent = Intent(context, PortfolioActivity::class.java)
            context.startActivity(intent)
        }

        holder.buttonBook.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            context.startActivity(intent)
        }

        return view!!
    }

    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var textViewName: TextView
        lateinit var textViewLocation: TextView
        lateinit var buttonViewPortfolio: Button
        lateinit var buttonBook: Button
    }
}
