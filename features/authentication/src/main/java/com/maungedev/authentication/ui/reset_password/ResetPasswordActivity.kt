package com.maungedev.authentication.ui.reset_password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.maungedev.authentication.databinding.ActivityResetPasswordBinding
import com.maungedev.authentication.di.authModule
import com.maungedev.authentication.ui.login.LoginViewModel
import com.maungedev.domain.utils.Resource
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(authModule)

        binding.btnResetPassword.setOnClickListener {
            viewModel.resetPassword(binding.tilResetPassword.editText?.text.toString())
                .observe(this, ::resetPasswordResponse)
        }

    }

    private fun resetPasswordResponse(resource: Resource<Unit>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                Snackbar.make(
                    binding.root,
                    "Reset Password telah dikirimkan ke email milikmu",
                    Snackbar.LENGTH_LONG
                )
                    .show()

            }
            is Resource.Loading -> {
                loadingState(true)
            }

            is Resource.Error -> {
                loadingState(false)
                Snackbar.make(binding.root, resource.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }

        }

    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnResetPassword.isEnabled = !state
        }
    }
}