package com.taxibooking.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.taxibooking.app.databinding.ActivityLoginBinding
import com.taxibooking.app.viewmodels.LoginViewModel
import com.taxibooking.app.R

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val email = binding.editEmail.text?.toString().orEmpty()
            val password = binding.editPassword.text?.toString().orEmpty()
            when {
                email.isBlank() -> {
                    Toast.makeText(this, getString(R.string.validation_email_empty), Toast.LENGTH_SHORT).show()
                }
                password.isBlank() -> {
                    Toast.makeText(this, getString(R.string.validation_password_empty), Toast.LENGTH_SHORT).show()
                }
                else -> viewModel.login(email, password)
            }
        }

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is LoginViewModel.LoginUiState.Idle -> showLoading(false)
                is LoginViewModel.LoginUiState.Loading -> showLoading(true)
                is LoginViewModel.LoginUiState.Success -> showLoading(false)
                is LoginViewModel.LoginUiState.Error -> {
                    showLoading(false)
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.navigateToMain.observe(this) { go ->
            if (go == true) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                viewModel.consumeNavigateToMain()
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.buttonLogin.isEnabled = !loading
    }
}
