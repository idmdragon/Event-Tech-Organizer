package com.maungedev.event.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maungedev.domain.model.Event
import com.maungedev.event.databinding.FragmentHomeBinding
import com.maungedev.eventtechorganizer.adapter.EventLayoutAdapter

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapter: EventLayoutAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.getMyEvent().observe(viewLifecycleOwner, ::setMyEvent)

        return binding.root
    }

    private fun setMyEvent(list: List<Event>) {
        adapter = EventLayoutAdapter(requireContext())
        adapter.setItems(list)
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}