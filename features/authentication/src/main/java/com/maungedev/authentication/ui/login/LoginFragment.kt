package com.maungedev.authentication.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.maungedev.authentication.R
import com.maungedev.authentication.databinding.FragmentLoginBinding
import com.maungedev.authentication.di.authModule
import com.maungedev.authentication.ui.register.RegisterViewModel
import com.maungedev.authentication.ui.register.RegisterFragment
import com.maungedev.domain.utils.Resource
import com.maungedev.eventtechorganizer.main.MainActivity
import org.koin.core.context.loadKoinModules
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(authModule)

        binding.apply {
            tvDaftar.setOnClickListener {
                moveToRegister()
            }
            btnLogin.setOnClickListener {
                val email = tilEmail.editText?.text.toString()
                val password = tilPassword.editText?.text.toString()
                viewModel.signIn(email, password).observe(viewLifecycleOwner, ::signInResponse)
                startActivity(Intent(requireContext(), MainActivity::class.java)).also {
                    activity?.finish()
                }

            }
        }
    }

        private fun signInResponse(resource: Resource<Unit>) {
            when (resource) {
                is Resource.Success -> {
                    loadingState(false)
                    startActivity(Intent(requireContext(), MainActivity::class.java)).also {
                        activity?.finish()
                    }
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


    private fun moveToRegister(){
        val mRegisterFragment = RegisterFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.frame_container,
                mRegisterFragment,
                RegisterFragment::class.java.simpleName
            )
            addToBackStack(null)
            commit()
        }
    }
    private fun loadingState(state: Boolean) {
        with(binding){
            progressBar.isVisible = state
            btnLogin.isEnabled = !state
        }
    }



}