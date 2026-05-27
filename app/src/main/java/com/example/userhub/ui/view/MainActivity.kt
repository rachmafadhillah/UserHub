package com.example.userhub.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userhub.databinding.ActivityMainBinding
import com.example.userhub.ui.adapter.LoadingStateAdapter
import com.example.userhub.ui.adapter.UserAdapter
import com.example.userhub.ui.viewmodel.UserViewModel
import com.example.userhub.ui.viewmodelfactory.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 🛠️ TAMBAHKAN INI: Pindahkan deklarasi adapter ke level class
    private val adapter = UserAdapter()

    private val mainViewModel: UserViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        binding.fabAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        getData()
    }

    private fun getData() {
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        mainViewModel.user.observe(this, {
            adapter.submitData(lifecycle, it)
        })
    }
}