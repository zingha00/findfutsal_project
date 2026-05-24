package com.utama.findfutsal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utama.findfutsall.data.model.Field
import com.utama.findfutsall.databinding.ItemFieldBinding

class FieldAdapter(
    private var fields: List<Field>,
    private val onItemClick: (Field) -> Unit
) : RecyclerView.Adapter<FieldAdapter.FieldViewHolder>() {

    inner class FieldViewHolder(val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val binding = ItemFieldBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FieldViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        val field = fields[position]
        with(holder.binding) {
            tvFieldName.text = field.name
            tvFieldAddress.text = field.address
            tvFieldPrice.text = "Rp${field.price}k/jam"
            tvFieldRating.text = field.rating.toString()
            tvFieldDistance.text = field.distance ?: ""

            // Load foto dari drawable lokal
            val context = root.context
            val photoName = field.photo ?: ""
            val resourceId = if (photoName.isNotEmpty()) {
                context.resources.getIdentifier(photoName, "drawable", context.packageName)
            } else 0

            if (resourceId != 0) {
                Glide.with(context)
                    .load(resourceId)
                    .centerCrop()
                    .into(ivFieldPhoto)
            } else {
                Glide.with(context)
                    .load(android.R.drawable.ic_menu_gallery)
                    .into(ivFieldPhoto)
            }

            root.setOnClickListener { onItemClick(field) }
        }
    }

    override fun getItemCount() = fields.size

    fun updateData(newFields: List<Field>) {
        fields = newFields
        notifyDataSetChanged()
    }
}