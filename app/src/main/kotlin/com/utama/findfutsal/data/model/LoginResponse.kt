package com.utama.findfutsal.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String?,

    @SerializedName("user")
    val user: User?
)

data class User(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phone")
    val phone: String?,

    @SerializedName("photo")
    val photo: String?,

    @SerializedName("role")
    val role: String,

    // Opsional — hapus jika API kamu tidak mengembalikan field ini
    @SerializedName("created_at")
    val createdAt: String? = null
) {
    /** Return true jika user adalah admin */
    fun isAdmin(): Boolean = role.equals("admin", ignoreCase = true)

    /** Gunakan name jika ada, fallback ke email */
    fun toDisplayName(): String = name.ifEmpty { email }
}