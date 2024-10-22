package com.beauterare.rare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.beauterare.rare.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Timer
import java.util.TimerTask

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var param1: String? = null
    private var param2: String? = null

    private val db = FirebaseFirestore.getInstance()

    private lateinit var viewPager: ViewPager
    private lateinit var timer: Timer
    private val DELAY_MS: Long = 1500
    private val PERIOD_MS: Long = 2000
    private var currentPage = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewPager = view.findViewById(R.id.viewPager)

        val imageList = listOf(
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3
        )

        val adapter = ImagePagerAdapter(imageList, requireContext())
        viewPager.adapter = adapter

        // Implement automatic sliding
        val update = Runnable {
            if (currentPage == adapter.count - 1) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
        }

        timer = Timer() // This will create a new Thread
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            },
            DELAY_MS,
            PERIOD_MS
        )

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {
                if (!name.isNullOrEmpty()) {
                    fetchSearchResults(name)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle text changes if needed
                return false
            }
        })

        // Set up button click listeners
        view.findViewById<ImageButton>(R.id.walkinbimg).setOnClickListener {
            walkinbimgClick(it)
        }

        view.findViewById<ImageButton>(R.id.homebimg).setOnClickListener {
            homebimgClick(it)
        }

        view.findViewById<ImageButton>(R.id.appointmentList).setOnClickListener {
            appointmentListClick(it)
        }

        return view
    }
    // Extension function to convert dp to pixels
    fun Int.dpToPx(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).toInt()
    }


    private fun fetchSearchResults(name: String) {
        val itemsRef = db.collection("artist")
        val searchQuery = itemsRef.whereEqualTo("artistname", name)

        searchQuery.get().addOnSuccessListener { querySnapshot ->
            binding.resultsContainer.removeAllViews()

            if (querySnapshot.isEmpty) {
                Log.d("HomeFragment", "No matching documents found.")
                val textView = TextView(requireContext()).apply {
                    text = "No artist found"
                    textSize = 16f
                    setPadding(8, 8, 8, 8)
                }
                binding.resultsContainer.addView(textView)
            } else {
                for (document in querySnapshot.documents) {
                    val artistName = document.getString("artistname")
                    val location = document.getString("artistlocation")
                    val workExperience = document.getString("artistworkex")
                    val profileImage = document.getString("artistprofile")
                    val artistO = document.getString("artistO") ?: ""
                    val artistT = document.getString("artistT") ?: ""
                    val artistTh = document.getString("artistTh") ?: ""
                    val artistFo = document.getString("artistFo") ?: ""
                    val artistFi = document.getString("artistFi") ?: ""
                    val artistS = document.getString("artistS") ?: ""
                    val artistSe = document.getString("artistSe") ?: ""
                    val artistE = document.getString("artistE") ?: ""
                    val artistN = document.getString("artistN") ?: ""

                    Log.d("HomeFragment", "Found artist: $artistName")


                    val artistLayout = LinearLayout(requireContext())


                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    artistLayout.layoutParams = layoutParams


                    val profileImageView = ImageView(requireContext())
                    val imageLayoutParams = LinearLayout.LayoutParams(
                        50.dpToPx(requireContext()),
                        50.dpToPx(requireContext())
                    )
                    profileImageView.layoutParams = imageLayoutParams
                    profileImage?.let {
                        Glide.with(requireContext())
                            .load(it)
                            .into(profileImageView)
                    }
                    artistLayout.addView(profileImageView)

                    val nameTextView = TextView(requireContext()).apply {
                        text = " $artistName"
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }
                    artistLayout.addView(nameTextView)


                    artistLayout.setOnClickListener {
                        val intent = Intent(requireContext(), ArtistDetailActivity::class.java).apply {
                            putExtra("ARTIST_NAME", artistName)
                            putExtra("ARTIST_LOCATION", location)
                            putExtra("ARTIST_WORK_EXPERIENCE", workExperience)
                            putExtra("ARTIST_PROFILE_IMAGE", profileImage)
                            putExtra("ARTIST_O", artistO)
                            putExtra("ARTIST_T", artistT)
                            putExtra("ARTIST_TH", artistTh)
                            putExtra("ARTIST_FO", artistFo)
                            putExtra("ARTIST_FI", artistFi)
                            putExtra("ARTIST_S", artistS)
                            putExtra("ARTIST_SE", artistSe)
                            putExtra("ARTIST_E", artistE)
                            putExtra("ARTIST_N", artistN)
                        }
                        startActivity(intent)
                    }


                    binding.resultsContainer.addView(artistLayout)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("HomeFragment", "Error fetching search results", e)
        }
    }


    private fun walkinbimgClick(view: View) {
        val intent = Intent(requireActivity(), WalkInActivity::class.java)
        startActivity(intent)
    }

    private fun homebimgClick(view: View) {
        val intent = Intent(requireActivity(), HomeSerActivity::class.java)
        startActivity(intent)
    }

    private fun appointmentListClick(view: View) {
        val intent = Intent(requireActivity(), DisplayAppointmentActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
