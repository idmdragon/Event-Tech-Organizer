package com.maungedev.event.ui.add_event.add_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maungedev.domain.model.Event
import com.maungedev.event.R
import com.maungedev.event.databinding.ActivityAddEventBinding
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_TYPE

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEventBinding
    private lateinit var viewModel:AddEventViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }



        addEvent()
    }

    private fun addEvent() {
        with(binding){

  /*          val event = Event(
                "",
                tilEventName.editText?.text.toString(),
                intent.getStringExtra(EVENT_TYPE).toString(),
                tilEventCategory.editText?.text.toString(),
                tilEventPrice.editText?.text.toString().toLong(),
                tilEventDate.editText?.text.toString(),
                tilEventTime.editText?.text.toString(),
                tilEventLocation.editText?.text.toString(),
                tilLinkRegistration.editText?.text.toString(),
                tilEventDescription.editText?.text.toString(),
                tilEventPrerequisite.editText?.text.toString(),

            )
            viewModel.addEvent()*/
        }

    }

}