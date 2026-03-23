package com.taxibooking.app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.taxibooking.app.databinding.ActivityMainBinding
import com.taxibooking.app.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRequestTaxi.setOnClickListener {
            viewModel.requestTaxi()
        }

        viewModel.requestStatus.observe(this) { status ->
            when (status) {
                is MainViewModel.RequestStatus.Idle -> {
                    binding.progressRequest.visibility = View.GONE
                    binding.buttonRequestTaxi.isEnabled = true
                }
                is MainViewModel.RequestStatus.Searching -> {
                    binding.buttonRequestTaxi.isEnabled = false
                    binding.progressRequest.visibility = View.VISIBLE
                    Toast.makeText(this, getString(R.string.taxi_searching), Toast.LENGTH_SHORT).show()
                }
                is MainViewModel.RequestStatus.DriverAssigned -> {
                    binding.progressRequest.visibility = View.GONE
                    binding.buttonRequestTaxi.isEnabled = true
                    Toast.makeText(this, getString(R.string.taxi_on_way), Toast.LENGTH_LONG).show()
                    binding.root.post { viewModel.clearToIdle() }
                }
            }
        }
    }
}
