package com.maungedev.insight.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.maungedev.domain.model.Event
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import com.maungedev.eventtechorganizer.adapter.EventInsightAdapter
import com.maungedev.insight.databinding.FragmentInsightBinding
import com.maungedev.insight.di.insightModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class InsightFragment : Fragment() {

    private val viewModel: InsightViewModel by viewModel()
    private lateinit var adapter: EventInsightAdapter
    private var _binding: FragmentInsightBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(insightModule)
        _binding = FragmentInsightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCurrentUser().observe(viewLifecycleOwner,::getCurrentUser)
    }

    private fun getCurrentUser(resource: Resource<User>) {
        when (resource) {

            is Resource.Success -> {
                loadingState(false)
                resource.data?.let {
                    it.myEvent?.let { events -> viewModel.getAllMyEvent(events).observe(viewLifecycleOwner, ::setInsight) }
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

    private fun setInsight(resource: Resource<List<Event>>?) {
        when (resource) {
            is Resource.Success -> {
                with(binding) {
                    loadingState(false)
                    adapter = EventInsightAdapter(requireContext())
                    resource.data?.let { adapter.setItems(it) }
                    rvInsight.adapter = adapter
                    rvInsight.layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL, false
                    )


                    viewModel.getTotalEvent()
                    viewModel.getTotalView()
                    viewModel.getTotalRegistrationClick()


                    viewModel.apply {
                        totalView.observe(viewLifecycleOwner, {
                            tvTotalSeen.text = it.toString()
                        })
                        totalRegistrationClick.observe(viewLifecycleOwner, {
                            tvTotalOpened.text = it.toString()
                        })
                        totalEvent.observe(viewLifecycleOwner, {
                            tvTotalEvent.text = it.toString()
                        })


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}