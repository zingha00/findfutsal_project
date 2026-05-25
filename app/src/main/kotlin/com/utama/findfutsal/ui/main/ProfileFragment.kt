profile package com.utama.findfutsal.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.utama.findfutsal.databinding.FragmentProfileBinding
import com.utama.findfutsal.ui.auth.LoginActivity
import com.utama.findfutsal.utils.SessionManager

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())

        setupUserData()
        setupClickListeners()
    }

    private fun setupUserData() {
        binding.tvName.text = sessionManager.getUserName() ?: "Nama User"
        binding.tvPhone.text = sessionManager.getUserPhone() ?: "+62 812 3456 7890"
    }

    private fun setupClickListeners() {
        binding.menuEditProfile.setOnClickListener {
            // TODO: buka EditProfileActivity
        }

        binding.menuPayment.setOnClickListener {
            // TODO: buka PaymentSettingActivity
        }

        binding.menuTerms.setOnClickListener {
            // TODO: buka TermsActivity
        }

        binding.menuHelp.setOnClickListener {
            // TODO: buka HelpActivity
        }

        binding.menuPrivacy.setOnClickListener {
            // TODO: buka PrivacyActivity
        }

        binding.btnDaftarPemilik.setOnClickListener {
            // TODO: buka RegisterOwnerActivity
        }

        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            // TODO: simpan preferensi notifikasi
        }

        binding.menuLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Keluar")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Keluar") { _, _ ->
                    sessionManager.clearSession()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finishAffinity()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}