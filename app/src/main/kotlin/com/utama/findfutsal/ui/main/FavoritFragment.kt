package com.utama.findfutsal.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utama.findfutsal.adapter.ExploreAdapter
import com.utama.findfutsal.data.model.Field
import com.utama.findfutsal.databinding.FragmentFavoriteBinding
import com.utama.findfutsal.ui.detail.DetailFieldActivity

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: ExploreAdapter
    private var allFavorites = listOf<Field>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChips()
        loadDummyData()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = ExploreAdapter(
            emptyList(),
            onItemClick = { field ->
                val intent = Intent(requireContext(), DetailFieldActivity::class.java)
                intent.putExtra("field_id", field.id)
                intent.putExtra("field_name", field.name)
                intent.putExtra("field_address", field.address)
                intent.putExtra("field_price", field.price)
                intent.putExtra("field_rating", field.rating)
                intent.putExtra("field_photo", field.photo)
                intent.putExtra("field_category", field.category)
                startActivity(intent)
            },
            onFavoriteClick = { field ->
                // TODO: hapus dari favorit
            }
        )
        binding.rvFavorite.adapter = favoriteAdapter
    }

    private fun setupChips() {
        binding.chipSemua.setOnClickListener {
            setActiveChip("semua")
            favoriteAdapter.updateData(allFavorites)
        }
        binding.chipFutsal.setOnClickListener {
            setActiveChip("futsal")
            favoriteAdapter.updateData(
                allFavorites.filter { it.category == "Indoor" || it.category == "Sintetis" || it.category == "Vinyl" }
            )
        }
        binding.chipBadminton.setOnClickListener {
            setActiveChip("badminton")
            favoriteAdapter.updateData(
                allFavorites.filter { it.category == "Badminton" }
            )
        }
        binding.chipBasket.setOnClickListener {
            setActiveChip("basket")
            favoriteAdapter.updateData(
                allFavorites.filter { it.category == "Basket" }
            )
        }
    }

    private fun setActiveChip(active: String) {
        binding.chipSemua.setBackgroundResource(
            if (active == "semua") com.utama.findfutsal.R.drawable.bg_chip_active
            else com.utama.findfutsal.R.drawable.bg_chip_inactive)
        binding.chipSemua.setTextColor(
            if (active == "semua") android.graphics.Color.WHITE
            else android.graphics.Color.parseColor("#121212"))

        binding.chipFutsal.setBackgroundResource(
            if (active == "futsal") com.utama.findfutsal.R.drawable.bg_chip_active
            else com.utama.findfutsal.R.drawable.bg_chip_inactive)
        binding.chipFutsal.setTextColor(
            if (active == "futsal") android.graphics.Color.WHITE
            else android.graphics.Color.parseColor("#121212"))

        binding.chipBadminton.setBackgroundResource(
            if (active == "badminton") com.utama.findfutsal.R.drawable.bg_chip_active
            else com.utama.findfutsal.R.drawable.bg_chip_inactive)
        binding.chipBadminton.setTextColor(
            if (active == "badminton") android.graphics.Color.WHITE
            else android.graphics.Color.parseColor("#121212"))

        binding.chipBasket.setBackgroundResource(
            if (active == "basket") com.utama.findfutsal.R.drawable.bg_chip_active
            else com.utama.findfutsal.R.drawable.bg_chip_inactive)
        binding.chipBasket.setTextColor(
            if (active == "basket") android.graphics.Color.WHITE
            else android.graphics.Color.parseColor("#121212"))
    }

    private fun loadDummyData() {
        allFavorites = listOf(
            Field(1, "Siliwangi Soccer Field", "Sumur Bandung",
                150, 4.8f, "field_1", "Indoor", "2.4 km"),
            Field(2, "The Hoop Urban Court", "Coblong",
                200, 4.5f, "field_2", "Basket", "4.1 km"),
            Field(3, "Parahyangan Sports", "Kota Bandung",
                60, 4.3f, "field_3", "Badminton", "3.2 km")
        )

        if (allFavorites.isEmpty()) {
            binding.layoutEmpty.visibility = View.VISIBLE
            binding.rvFavorite.visibility = View.GONE
        } else {
            binding.layoutEmpty.visibility = View.GONE
            binding.rvFavorite.visibility = View.VISIBLE
            favoriteAdapter.updateData(allFavorites)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}