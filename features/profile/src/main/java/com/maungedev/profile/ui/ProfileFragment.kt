package com.maungedev.profile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import com.maungedev.eventtechorganizer.constant.USERNAME
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
                        }

                        tvEditUsername.setOnClickListener {
                            if (user != null) {
                                startActivity(Intent(requireContext(),EditUsernameActivity::class.java).putExtra(
                                    USERNAME,user.username))
                            }
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

    private fun loadingState(b: Boolean) {

    }
}