package com.maungedev.event.ui.add_event.choose_event

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.maungedev.event.databinding.FragmentChooseEventBinding
import com.maungedev.event.ui.add_event.add_event.AddEventViewModel
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_COMPETITION
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_CONFERENCE
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_TYPE
import com.maungedev.eventtechorganizer.constant.PageNameConstant.ADD_EVENT_PAGE

class ChooseEventFragment : Fragment() {

    private var _binding: FragmentChooseEventBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseEventBinding.inflate(inflater, container, false)
        binding.btnAddCompetition.setOnClickListener {
            startActivity(
                Intent(requireContext(), Class.forName(ADD_EVENT_PAGE)).putExtra(
                    EVENT_TYPE, EVENT_COMPETITION
                )
            )
        }
        binding.btnAddConference.setOnClickListener {
            startActivity(
                Intent(requireContext(), Class.forName(ADD_EVENT_PAGE)).putExtra(
                    EVENT_TYPE, EVENT_CONFERENCE
                )
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}