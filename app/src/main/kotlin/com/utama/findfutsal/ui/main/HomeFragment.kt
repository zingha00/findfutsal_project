package com.utama.findfutsal.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.utama.findfutsal.R
import com.utama.findfutsal.adapter.FieldAdapter
import com.utama.findfutsal.adapter.PromoAdapter
import com.utama.findfutsal.data.model.Field
import com.utama.findfutsal.data.model.Promo
import com.utama.findfutsal.databinding.FragmentHomeBinding
import com.utama.findfutsal.ui.detail.DetailFieldActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var fieldAdapter: FieldAdapter
    private lateinit var promoAdapter: PromoAdapter
    private val handler = Handler(Looper.getMainLooper())
    private var currentPage = 0
    private val dots = mutableListOf<ImageView>()

    private val slideRunnable = Runnable {
        _binding?.let { binding ->
            val promoCount = promoAdapter.itemCount
            if (promoCount > 0) {
                currentPage = (currentPage + 1) % promoCount
                binding.vpPromo.setCurrentItem(currentPage, true)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPromo()
        setupRecyclerView()
        setupClickListeners()
        loadDummyData()

        // Hapus rvCategory karena adapternya tidak ada
        binding.rvCategory.visibility = View.GONE
    }

    private fun setupPromo() {
        val promos = listOf(
            Promo(1, "Diskon 30%", "SPECIAL WEEKEND",
                "Main bareng tim jadi makin hemat!", "#00A86B", null),
            Promo(2, "Gratis 1 Jam", "FIRST BOOKING",
                "Booking pertama gratis 1 jam!", "#1A4D2E", null),
            Promo(3, "Cashback 20%", "PROMO BULANAN",
                "Bayar pakai dompet digital dapat cashback!", "#00796B", null)
        )

        promoAdapter = PromoAdapter(promos)
        binding.vpPromo.adapter = promoAdapter
        binding.vpPromo.offscreenPageLimit = 1

        setupDots(promos.size)

        binding.vpPromo.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                updateDots(position)
                handler.removeCallbacks(slideRunnable)
                handler.postDelayed(slideRunnable, 3000)
            }
        })

        handler.postDelayed(slideRunnable, 3000)
    }

    private fun setupDots(count: Int) {
        dots.clear()
        binding.layoutDots.removeAllViews()
        for (i in 0 until count) {
            val dot = ImageView(requireContext())
            val params = LinearLayout.LayoutParams(8, 8).apply {
                setMargins(4, 0, 4, 0)
            }
            dot.layoutParams = params
            dot.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.dot_inactive)
            )
            dots.add(dot)
            binding.layoutDots.addView(dot)
        }
        updateDots(0)
    }

    private fun updateDots(position: Int) {
        val context = context ?: return
        dots.forEachIndexed { index, dot ->
            dot.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (index == position) R.drawable.dot_active else R.drawable.dot_inactive
                )
            )
        }
    }

    private fun setupRecyclerView() {
        fieldAdapter = FieldAdapter(emptyList()) { field ->
            val intent = Intent(requireContext(), DetailFieldActivity::class.java)
            intent.putExtra("field_name", field.name)
            intent.putExtra("field_address", field.address)
            intent.putExtra("field_price", field.price)
            intent.putExtra("field_rating", field.rating)
            intent.putExtra("field_photo", field.photo)
            intent.putExtra("field_category", field.category)
            startActivity(intent)
        }
        binding.rvFields.adapter = fieldAdapter
    }

    private fun setupClickListeners() {
        binding.tvSearch.setOnClickListener {
            (activity as? com.utama.findfutsall.MainActivity)
                ?.setSelectedNavItem(R.id.nav_explore)
        }
    }

    private fun loadDummyData() {
        val dummyFields = listOf(
            Field(1, "Siliwangi Indoor Futsal", "Sumur Bandung",
                150, 4.8f, "field_1", "Indoor", "2.4 km"),
            Field(2, "Parahyangan Turf Arena", "Coblong",
                120, 4.5f, "field_2", "Sintetis", "4.1 km"),
            Field(3, "Arena Master Bandung", "Kota Bandung",
                100, 4.8f, "field_3", "Vinyl", "3.2 km"),
            Field(4, "GOR Lodaya Futsal", "Lengkong",
                90, 4.3f, "field_4", "Sintetis", "5.1 km"),
            Field(5, "Futsal Planet Bandung", "Antapani",
                110, 4.6f, "field_5", "Vinyl", "6.2 km")
        )
        fieldAdapter.updateData(dummyFields)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(slideRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(slideRunnable)
        _binding = null
    }
}