package com.maungedev.event.ui.home

import android.content.Intent
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
import com.maungedev.domain.utils.Resource
import com.maungedev.event.databinding.FragmentHomeBinding
import com.maungedev.event.di.eventModule
import com.maungedev.eventtechorganizer.adapter.EventLayoutAdapter
import com.maungedev.eventtechorganizer.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: EventLayoutAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadKoinModules(eventModule)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listEvent = ArrayList<String>()
        listEvent.add("domUwPDh73JDpDVxEVaq")
        listEvent.add("aMSG78VLRp58lnQ0I61o")
        listEvent.add("HBz1YofDoLNqm8m71ydi")

        viewModel.getAllMyEvent(listEvent).observe(viewLifecycleOwner, ::setMyEvent)
        binding.tvTitle.setOnClickListener {
            viewModel.deleteEvent("k3UGhpbVUmHQIaw52l3T").observe(viewLifecycleOwner, ::deleteResponse)
        }

    }

    private fun deleteResponse(resource: Resource<Unit>?) {

        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                Snackbar.make(binding.root, "Event berhasil di hapus", Snackbar.LENGTH_LONG)
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

    private fun setMyEvent(resource: Resource<List<Event>>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                adapter = EventLayoutAdapter(requireContext())
                resource.data?.let { adapter.setItems(it) }
                binding.rvHome.adapter = adapter
                binding.rvHome.layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.VERTICAL, false
                )

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