package com.maungedev.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.maungedev.domain.model.Event
import com.maungedev.eventtechorganizer.adapter.EventInsightAdapter
import com.maungedev.insight.databinding.FragmentInsightBinding

class InsightFragment : Fragment() {

    private val viewModel: InsightViewModel by activityViewModels()
    private lateinit var adapter: EventInsightAdapter
    private var _binding: FragmentInsightBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsightBinding.inflate(inflater, container, false)
        viewModel.getMyEvent().observe(viewLifecycleOwner, ::setInsight)

        return binding.root
    }

    private fun setInsight(list: List<Event>) {
        adapter = EventInsightAdapter(requireContext())
        adapter.setItems(list)
        binding.rvInsight.adapter = adapter
        binding.rvInsight.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        binding.tvTotalEvent.text = list.size.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}