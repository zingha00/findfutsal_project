package com.utama.findfutsal.ui.detail

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.gridlayout.widget.GridLayout
import com.utama.findfutsal.databinding.ActivityDetailFieldBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DetailFieldActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFieldBinding
    private var selectedTimeSlot: String? = null
    private var fieldPrice = 150000

    // Dummy slot yang sudah dibooking
    private val bookedSlots = listOf("09:00", "12:00", "15:00")

    private val timeSlots = listOf(
        "08:00", "09:00", "10:00",
        "11:00", "12:00", "13:00",
        "14:00", "15:00", "16:00",
        "17:00", "18:00", "19:00"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFieldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        val fieldName = intent.getStringExtra("field_name") ?: "Lapangan"
        val fieldAddress = intent.getStringExtra("field_address") ?: ""
        val fieldPrice = intent.getIntExtra("field_price", 150)
        val fieldRating = intent.getFloatExtra("field_rating", 0f)
        val fieldPhoto = intent.getStringExtra("field_photo")
        val fieldCategory = intent.getStringExtra("field_category") ?: ""

        this.fieldPrice = fieldPrice * 1000

        // Set data ke view
        binding.tvFieldName.text = fieldName
        binding.tvAddress.text = fieldAddress
        binding.tvRating.text = fieldRating.toString()
        binding.tvCategory.text = fieldCategory
        binding.tvPriceWeekday.text = "Rp ${fieldPrice}.000"
        binding.tvPriceWeekend.text = "Rp ${fieldPrice + 50}.000"

        // Load foto
        val resourceId = resources.getIdentifier(
            fieldPhoto, "drawable", packageName
        )
        if (resourceId != 0) {
            binding.ivFieldPhoto.setImageResource(resourceId)
        }

        setupClickListeners()
        setupDatePicker()
        setupTimeSlots()

        // Sembunyikan rincian & bottom bar dulu
        binding.cardRincian.visibility = View.GONE
        binding.btnBookNow.isEnabled = false
        binding.btnBookNow.alpha = 0.5f
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener { finish() }

        binding.btnBookNow.setOnClickListener {
            if (selectedTimeSlot != null && binding.etDate.text?.isNotEmpty() == true) {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("field_name", binding.tvFieldName.text.toString())
                intent.putExtra("field_address", binding.tvAddress.text.toString())
                intent.putExtra("field_date", binding.etDate.text.toString())
                intent.putExtra("field_time", selectedTimeSlot)
                intent.putExtra("field_price", fieldPrice)
                startActivity(intent)
            }
        }
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    val selectedDate = String.format("%02d/%02d/%04d", day, month + 1, year)
                    binding.etDate.setText(selectedDate)
                    // Reset slot saat tanggal berubah
                    selectedTimeSlot = null
                    binding.cardRincian.visibility = View.GONE
                    binding.btnBookNow.isEnabled = false
                    binding.btnBookNow.alpha = 0.5f
                    setupTimeSlots()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupTimeSlots() {
        binding.gridTimeSlots.removeAllViews()

        timeSlots.forEach { slot ->
            val isBooked = bookedSlots.contains(slot)
            val tv = TextView(this)
            val params = GridLayout.LayoutParams()
            params.width = 0
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.setMargins(6, 6, 6, 6)
            tv.layoutParams = params
            tv.text = slot
            tv.textSize = 13f
            tv.gravity = android.view.Gravity.CENTER
            tv.setPadding(0, 24, 0, 24)

            if (isBooked) {
                tv.setBackgroundColor(Color.parseColor("#F5F5F5"))
                tv.setTextColor(Color.parseColor("#999999"))
                tv.isEnabled = false
            } else {
                tv.setBackgroundResource(com.utama.findfutsal.R.drawable.bg_slot_available)
                tv.setTextColor(Color.parseColor("#00A86B"))
                tv.setOnClickListener {
                    selectedTimeSlot = slot
                    updateSlotSelection(slot)
                    showRincian()
                }
            }

            binding.gridTimeSlots.addView(tv)
        }
    }

    private fun updateSlotSelection(selectedSlot: String) {
        for (i in 0 until binding.gridTimeSlots.childCount) {
            val child = binding.gridTimeSlots.getChildAt(i) as? TextView
            child?.let {
                val slot = it.text.toString()
                if (!bookedSlots.contains(slot)) {
                    if (slot == selectedSlot) {
                        it.setBackgroundResource(com.utama.findfutsal.R.drawable.bg_slot_selected)
                        it.setTextColor(Color.WHITE)
                    } else {
                        it.setBackgroundResource(com.utama.findfutsal.R.drawable.bg_slot_available)
                        it.setTextColor(Color.parseColor("#00A86B"))
                    }
                }
            }
        }
    }

    private fun showRincian() {
        val serviceFee = 5000
        val total = fieldPrice + serviceFee

        binding.tvRincianSewa.text = "Rp ${formatPrice(fieldPrice)}"
        binding.tvTotalRincian.text = "Rp ${formatPrice(total)}"
        binding.tvTotalPrice.text = "Rp ${formatPrice(total)} / jam"

        binding.cardRincian.visibility = View.VISIBLE
        binding.btnBookNow.isEnabled = true
        binding.btnBookNow.alpha = 1f
    }

    private fun formatPrice(price: Int): String {
        return String.format("%,d", price).replace(",", ".")
    }
}