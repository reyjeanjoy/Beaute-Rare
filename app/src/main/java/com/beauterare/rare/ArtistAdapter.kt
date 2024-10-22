package com.beauterare.rare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beauterare.rare.databinding.ItemArtistBinding


class ArtistAdapter(
    private val context: Context,
    private var artistList: List<Artist>
) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

    fun updateArtistList(newArtistList: List<Artist>) {
        artistList = newArtistList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = artistList[position]
        holder.bind(artist)
    }

    override fun getItemCount(): Int = artistList.size

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.artistName.text = artist.artistname

            // You can bind other artist properties similarly
        }
    }
}
