package com.utama.findfutsal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utama.findfutsall.data.model.Field
import com.utama.findfutsall.databinding.ItemFieldExploreBinding

class ExploreAdapter(
    private var fields: List<Field>,
    private val onItemClick: (Field) -> Unit,
    private val onFavoriteClick: (Field) -> Unit
) : RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>() {

    inner class ExploreViewHolder(val binding: ItemFieldExploreBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val binding = ItemFieldExploreBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ExploreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        val field = fields[position]
        with(holder.binding) {
            tvFieldName.text = field.name
            tvRating.text = field.rating.toString()
            tvDistance.text = field.distance ?: ""
            tvPrice.text = "Rp ${field.price}k/jam"

            val context = root.context
            val resourceId = context.resources.getIdentifier(
                field.photo, "drawable", context.packageName
            )
            if (resourceId != 0) {
                Glide.with(context)
                    .load(resourceId)
                    .centerCrop()
                    .into(ivFieldPhoto)
            }

            root.setOnClickListener { onItemClick(field) }
            ivFavorite.setOnClickListener { onFavoriteClick(field) }
        }
    }

    override fun getItemCount() = fields.size

    fun updateData(newFields: List<Field>) {
        fields = newFields
        notifyDataSetChanged()
    }
}