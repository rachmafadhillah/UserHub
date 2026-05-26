package com.example.userhub.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.userhub.R
import com.example.userhub.databinding.ActivityAddUserBinding
import com.example.userhub.ui.viewmodel.AddUserViewModel
import com.example.userhub.ui.viewmodelfactory.ViewModelFactory

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding

    // 🛠️ Inisialisasi AddUserViewModel menggunakan Factory
    private val addUserViewModel: AddUserViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        supportActionBar?.title = "Add New User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        // 🛠️ 1. Siapkan Observer untuk mendengarkan hasil dari ViewModel
        addUserViewModel.addResult.observe(this) { result ->
            showLoading(false)

            result.onSuccess { userItem ->
                Toast.makeText(this, "User ${userItem.name} berhasil ditambahkan!", Toast.LENGTH_LONG).show()
                finish() // Menutup halaman dan otomatis kembali ke MainActivity
            }.onFailure { exception ->
                Toast.makeText(this, "Gagal menambahkan data: ${exception.message}", Toast.LENGTH_LONG).show()
            }
        }

        // 🛠️ 2. Atur aksi ketika tombol Save diklik
        binding.btnSave.setOnClickListener {
            val name = binding.edUsername.text.toString().trim()
            val email = binding.edEmail.text.toString().trim()
            val phoneNumber = binding.edPhone.text.toString().trim()
            val city = binding.edCity.text.toString().trim()
            val address = binding.edAddress.text.toString().trim()
            val gender = if (binding.rbMale.isChecked) 0 else 1

            // Validasi input kosong
            if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || city.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            showLoading(true)

            // Panggil fungsi di ViewModel (Tidak perlu lifecycleScope.launch lagi di sini)
            addUserViewModel.addUser(name, address, email, phoneNumber, city, gender)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.materialBarDetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(true)
            title = "Tambah User"
        }
        binding.materialBarDetail.setNavigationOnClickListener { onBackPressed() }
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnSave.isEnabled = !isLoading
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}