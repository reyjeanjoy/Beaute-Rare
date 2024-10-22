import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.beauterare.rare.R
import com.bumptech.glide.Glide

class GalleryPagerAdapter(
    private val context: Context,
    private val imageUrls: List<String>
) : RecyclerView.Adapter<GalleryPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewGallery)
    }
}
