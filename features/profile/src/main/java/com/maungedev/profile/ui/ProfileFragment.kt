package com.maungedev.profile.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EMAIL
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.USERNAME
import com.maungedev.eventtechorganizer.constant.PageNameConstant.ABOUT_PAGE
import com.maungedev.eventtechorganizer.constant.PageNameConstant.AUTHENTICATION_PAGE
import com.maungedev.eventtechorganizer.constant.PageNameConstant.RESET_PASSWORD_PAGE
import com.maungedev.profile.databinding.FragmentProfileBinding
import com.maungedev.profile.di.profileModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModel()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(profileModule)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentUser().observe(viewLifecycleOwner, ::setProfileView)
        logout()
    }

    private fun setProfileView(resource: Resource<User>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)

                binding.apply {
                    resource.data.let { user ->

                        if (user != null) {
                            tvEmail.text = user.email
                            tvUsername.text = user.username

                            tvEditUsername.setOnClickListener {
                                startActivity(
                                    Intent(
                                        requireContext(),
                                        EditUsernameActivity::class.java
                                    ).putExtra(
                                        USERNAME, user.username
                                    )
                                )
                            }

                            tvEditPassword.setOnClickListener {
                                startActivity(
                                    Intent(
                                        requireContext(),
                                        Class.forName(RESET_PASSWORD_PAGE)
                                    ).putExtra(EMAIL, user.email)
                                )
                            }
                        }

                        tvAbout.setOnClickListener {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    Class.forName(ABOUT_PAGE)
                                )
                            )
                        }
                    }
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

    private fun loadingState(state: Boolean) {
        binding.progressBar.isVisible = state
    }

    private fun logout() {
        binding.tvLogout.setOnClickListener {
            viewModel.logout()
            requireContext().startActivity(
                Intent(
                    requireContext(),
                    Class.forName(AUTHENTICATION_PAGE)
                )
            ).also {
                activity?.finishAffinity()
            }
        }
    }


}