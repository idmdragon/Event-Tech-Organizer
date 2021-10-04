package com.maungedev.insight

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maungedev.insight.databinding.FragmentInsightBinding

class InsightFragment : Fragment() {

    private lateinit var insightViewModel: InsightViewModel
    private var _binding: FragmentInsightBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        insightViewModel =
            ViewModelProvider(this).get(InsightViewModel::class.java)

        _binding = FragmentInsightBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}