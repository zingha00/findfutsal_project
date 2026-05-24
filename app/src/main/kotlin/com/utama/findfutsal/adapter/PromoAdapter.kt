package com.utama.findfutsal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utama.findfutsal.data.model.Promo
import com.utama.findfutsal.databinding.ItemPromoBinding

class PromoAdapter(
    private val promos: List<Promo>
) : RecyclerView.Adapter<PromoAdapter.PromoViewHolder>() {

    inner class PromoViewHolder(val binding: ItemPromoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromoViewHolder {
        val binding = ItemPromoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PromoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromoViewHolder, position: Int) {
        val promo = promos[position]
        with(holder.binding) {
            tvPromoTitle.text = promo.title
            tvPromoSubtitle.text = promo.subtitle
            tvPromoDesc.text = promo.description
            root.setCardBackgroundColor(Color.parseColor(promo.backgroundColor))
        }
    }

    override fun getItemCount() = promos.size
}