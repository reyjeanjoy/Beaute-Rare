package com.beauterare.rare

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

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val db = FirebaseFirestore.getInstance()

    private lateinit var viewPager: ViewPager
    private lateinit var viewPager2: ViewPager
    private lateinit var timer: Timer
    private val DELAY_MS: Long = 1500
    private val PERIOD_MS: Long = 2000
    private var currentPage = 0
    private var currentPage2 = 0
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize ViewPager for the first set of images
        viewPager = view.findViewById(R.id.viewPager)
        setupViewPager(viewPager, listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3), isFirstPager = true)

        // Initialize ViewPager2 for the second set of images
        viewPager2 = view.findViewById(R.id.viewPager2)
        setupViewPager(viewPager2, listOf(R.drawable.m1, R.drawable.m2, R.drawable.m3, R.drawable.m4, R.drawable.m5,R.drawable.m6), isFirstPager = false)

        // Search bar listener
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(name: String?): Boolean {
                if (!name.isNullOrEmpty()) {
                    fetchSearchResults(name)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // Set up `clickbtn` button listener
        view.findViewById<TextView>(R.id.clickbtn).setOnClickListener {
            clickbtnClick(it)
        }

        // Appointment list button listener
        view.findViewById<ImageButton>(R.id.appointmentList).setOnClickListener {
            appointmentListClick(it)
        }

        return view
    }

    private fun setupViewPager(viewPager: ViewPager, imageList: List<Int>, isFirstPager: Boolean) {
        val adapter = ImagePagerAdapter(imageList, requireContext())
        viewPager.adapter = adapter

        val update = Runnable {
            val currentPageRef = if (isFirstPager) currentPage else currentPage2
            val nextPage = if (currentPageRef == adapter.count - 1) 0 else currentPageRef + 1

            if (isFirstPager) {
                currentPage = nextPage
            } else {
                currentPage2 = nextPage
            }
            viewPager.setCurrentItem(nextPage, true)
        }

        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    handler.post(update)
                }
            },
            DELAY_MS,
            PERIOD_MS
        )
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

                    val artistLayout = LinearLayout(requireContext()).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }

                    val profileImageView = Glide.with(requireContext()).let {
                        val imageView = ImageView(requireContext())
                        it.load(profileImage).into(imageView)
                        imageView
                    }

                    artistLayout.addView(profileImageView)

                    val nameTextView = TextView(requireContext()).apply {
                        text = artistName
                        textSize = 16f
                        setPadding(8, 8, 8, 8)
                    }
                    artistLayout.addView(nameTextView)

                    artistLayout.setOnClickListener {
                        val intent = Intent(requireContext(), ArtistDetailActivity::class.java)
                        intent.putExtra("ARTIST_NAME", artistName)
                        startActivity(intent)
                    }

                    binding.resultsContainer.addView(artistLayout)
                }
            }
        }.addOnFailureListener { e ->
            Log.e("HomeFragment", "Error fetching search results", e)
        }
    }

    private fun clickbtnClick(view: View) {
        val intent = Intent(requireActivity(), HomeSerActivity::class.java) // Update with desired activity
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
}
