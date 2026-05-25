package com.utama.findfutsal.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.utama.findfutsal.adapter.ExploreAdapter
import com.utama.findfutsal.data.model.Field
import com.utama.findfutsal.databinding.FragmentExploreBinding
import com.utama.findfutsal.ui.detail.DetailFieldActivity

class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var exploreAdapter: ExploreAdapter
    private var allFields = listOf<Field>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearch()
        loadDummyData()
    }

    private fun setupRecyclerView() {
        exploreAdapter = ExploreAdapter(
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
                // TODO: tambah ke favorit
            }
        )
        binding.rvSearchResult.adapter = exploreAdapter
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterFields(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterFields(query: String) {
        val filtered = if (query.isEmpty()) {
            allFields
        } else {
            allFields.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.address.contains(query, ignoreCase = true) ||
                        it.category.contains(query, ignoreCase = true)
            }
        }
        exploreAdapter.updateData(filtered)
        binding.tvResultCount.text = "${filtered.size} Lapangan ditemukan"
    }

    private fun loadDummyData() {
        allFields = listOf(
            Field(1, "Siliwangi Soccer Center", "Sumur Bandung",
                150, 4.8f, "field_1", "Indoor", "1.2 km"),
            Field(2, "Parahyangan Futsal", "Coblong",
                120, 4.5f, "field_2", "Sintetis", "3.5 km"),
            Field(3, "GOR Pajajaran Arena", "Pajajaran",
                200, 4.9f, "field_3", "Vinyl", "4.1 km"),
            Field(4, "GOR Lodaya Futsal", "Lengkong",
                90, 4.3f, "field_4", "Sintetis", "5.1 km"),
            Field(5, "Futsal Planet Bandung", "Antapani",
                110, 4.6f, "field_5", "Vinyl", "6.2 km")
        )
        exploreAdapter.updateData(allFields)
        binding.tvResultCount.text = "${allFields.size} Lapangan ditemukan"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}