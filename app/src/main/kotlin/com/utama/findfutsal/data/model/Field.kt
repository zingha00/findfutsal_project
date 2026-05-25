package com.utama.findfutsal.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Field(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("price")
    val price: Int,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("category")
    val category: String,

    @SerializedName("distance")
    val distance: String?
) : Parcelable