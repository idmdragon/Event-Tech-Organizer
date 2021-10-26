package com.maungedev.event.ui.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.maungedev.domain.model.CompetitionCategory
import com.maungedev.domain.model.ConferenceCategory
import com.maungedev.domain.model.Event
import com.maungedev.domain.utils.Resource
import com.maungedev.event.R
import com.maungedev.event.databinding.ActivityEditEventBinding
import com.maungedev.event.di.eventModule
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant
import com.maungedev.eventtechorganizer.main.MainActivity
import com.maungedev.eventtechorganizer.utils.DateConverter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class EditEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEventBinding
    private val viewModel: EditEventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(eventModule)
        binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()

        viewModel.getEventById(intent.getStringExtra(ExtraNameConstant.EVENT_UID) as String)
            .observe(this@EditEventActivity, ::setEditTextValue)

    }

    private fun setEditTextValue(resource: Resource<Event>?) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                if (resource.data != null) {
                    setEditTextView(resource.data as Event)
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

    private fun setEditTextView(data: Event) {

        binding.apply {
            tilEventName.editText?.setText(data.eventName)
            tilEventCategory.editText?.setText(data.eventCategory)
            tilEventPrice.editText?.setText(data.price.toString())
            tilEventDate.editText?.setText(DateConverter.convertMillisToString(data.date))
            tilEventTime.editText?.setText(data.time)
            tilEventLocation.editText?.setText(data.location)
            tilLinkRegistration.editText?.setText(data.linkRegistration)
            tilEventDescription.editText?.setText(data.description)
            tilEventPrerequisite.editText?.setText(data.prerequisite)
            Glide.with(binding.root)
                .load(data.eventCover)
                .transform(CenterCrop(), RoundedCorners(8))
                .into(binding.ivAddCover)
            typeEvent(data.eventType)
        }
        binding.btnUpdateEvent.setOnClickListener {
            if (isEventNotEmpty()) {
                updateEvent(data)
            }
        }

    }

    private fun typeEvent(type: String) {
        if (type == ExtraNameConstant.EVENT_COMPETITION) {
            viewModel.getCompetitionCategory().observe(this, ::setCompetitionCategory)
        } else if (type == ExtraNameConstant.EVENT_CONFERENCE) {
            viewModel.getConferenceCategory().observe(this, ::setConferenceCategory)
        }
    }

    private fun setCompetitionCategory(resource: Resource<List<CompetitionCategory>>) {
        when (resource) {
            is Resource.Success -> {
                val items = ArrayList<String>()
                resource.data?.map {
                    items.add(it.categoryName)
                }
                val adapter =
                    ArrayAdapter(
                        this@EditEventActivity,
                        R.layout.category_list_dropdown_menu,
                        items
                    )
                binding.autoCompleteTextCategory.setAdapter(adapter)
            }
            is Resource.Error -> Snackbar.make(
                binding.root,
                "Category Event Error Loaded",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setConferenceCategory(resource: Resource<List<ConferenceCategory>>) {
        when (resource) {
            is Resource.Success -> {
                val items = ArrayList<String>()
                resource.data?.map {
                    items.add(it.categoryName)
                }
                val adapter =
                    ArrayAdapter(
                        this@EditEventActivity,
                        R.layout.category_list_dropdown_menu,
                        items
                    )
                binding.autoCompleteTextCategory.setAdapter(adapter)
            }
            is Resource.Error -> Snackbar.make(
                binding.root,
                "Category Event Error Loaded",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setView() {
        with(binding) {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            ivAddCover.setOnClickListener {
                Toast.makeText(this@EditEventActivity,"Mohon untuk Poster tidak bisa diubah",Toast.LENGTH_LONG).show()
            }

            tieEventDate.setOnClickListener {
                setEventDate()

            }
            tieEventTime.setOnClickListener {
                setEventTime()
            }

        }
    }

    private fun updateEvent(data: Event) {
        with(binding) {
                val event = Event(
                    uid = data.uid,
                    eventName = tilEventName.editText?.text.toString(),
                    eventType = data.eventType,
                    eventCategory =  tilEventCategory.editText?.text.toString(),
                    price = tilEventPrice.editText?.text.toString().toLong(),
                    date =  DateConverter.convertStringToMillis(tilEventDate.editText?.text.toString()),
                    time =  tilEventTime.editText?.text.toString(),
                    location =  tilEventLocation.editText?.text.toString(),
                    linkRegistration =  tilLinkRegistration.editText?.text.toString(),
                    description = tilEventDescription.editText?.text.toString(),
                    prerequisite =  tilEventPrerequisite.editText?.text.toString(),
                    eventCover =  data.eventCover,
                    numbersOfView =  data.numbersOfView,
                    numbersOfRegistrationClick =  data.numbersOfRegistrationClick,
                    organizer =  data.organizer,
                    organizerUid =  data.organizerUid,
                )
                viewModel.updateEvent(event).observe(this@EditEventActivity, ::addEventResponse)
            }
        }


    private fun addEventResponse(resource: Resource<Unit>) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                startActivity(Intent(this@EditEventActivity, MainActivity::class.java)).also {
                    finish()
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


    private fun isEventNotEmpty(): Boolean {
        with(binding) {
            val error = "Field tidak boleh kosong"
            when {
                tilEventName.editText?.text.toString().isEmpty() -> {
                    tilEventName.editText?.error = error
                }
                tilEventCategory.editText?.text.toString().isEmpty() -> {
                    tilEventName.editText?.error = error
                }
                tilEventPrice.editText?.text.toString().isEmpty() -> {
                    tilEventPrice.editText?.error = error
                }
                tilEventDate.editText?.text.toString().isEmpty() -> {
                    tilEventDate.editText?.error = error
                }
                tilEventTime.editText?.text.toString().isEmpty() -> {
                    tilEventTime.editText?.error = error
                }
                tilLinkRegistration.editText?.text.toString().isEmpty() -> {
                    tilLinkRegistration.editText?.error = error
                }
                tilEventDescription.editText?.text.toString().isEmpty() -> {
                    tilEventDescription.editText?.error = error
                }
                tilEventPrerequisite.editText?.text.toString().isEmpty() -> {
                    tilEventPrerequisite.editText?.error = error
                }

                else -> {
                    return true
                }
            }
            return false
        }
    }



    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnUpdateEvent.isEnabled = !state
        }
    }

    private fun setEventTime() {
        val pickerEventTimeBuilder =
            MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()

        pickerEventTimeBuilder.show(supportFragmentManager, "fragment_tag")
        pickerEventTimeBuilder.addOnPositiveButtonClickListener {
            val time = "${pickerEventTimeBuilder.hour}:${pickerEventTimeBuilder.minute}"
            binding.tilEventTime.editText?.setText(time)
        }
    }

    private fun setEventDate() {
        val builderEventDate = MaterialDatePicker.Builder.datePicker().build()
        builderEventDate.show(supportFragmentManager, builderEventDate.toString())
        builderEventDate.addOnPositiveButtonClickListener {
            binding.tilEventDate.editText?.setText(builderEventDate.headerText)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }
}