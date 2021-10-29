package com.maungedev.event.ui.add.add_event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.*
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
import com.maungedev.domain.model.User
import com.maungedev.domain.utils.Resource
import com.maungedev.event.R
import com.maungedev.event.databinding.ActivityAddEventBinding
import com.maungedev.event.di.eventModule
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_COMPETITION
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_CONFERENCE
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_TYPE
import com.maungedev.eventtechorganizer.main.MainActivity
import com.maungedev.eventtechorganizer.utils.DateConverter
import org.koin.core.context.loadKoinModules
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.unloadKoinModules
import java.util.*
import kotlin.collections.ArrayList

class AddEventActivity : AppCompatActivity() {

    companion object {
        const val GALLERY_PICK_REQUEST_CODE = 200
    }

    private lateinit var binding: ActivityAddEventBinding
    private val viewModel: AddEventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        unloadKoinModules(eventModule)
        loadKoinModules(eventModule)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()

        typeEvent(intent.getStringExtra(EVENT_TYPE) as String)
        viewModel.getCurrentUser().observe(this, {
            if (it.data != null) {
                viewModel.setCurrentUser(it.data as User)
            }
        })


    }

    private fun typeEvent(type: String) {
        if (type == EVENT_COMPETITION) {
            viewModel.getCompetitionCategory().observe(this, ::setCompetitionCategory)
        } else if (type == EVENT_CONFERENCE) {
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
                        this@AddEventActivity,
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
                        this@AddEventActivity,
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
                pickImage()
            }

            btnAddEvent.setOnClickListener {
                if (isEventNotEmpty()) {
                    addEvent()
                }
            }
            tilEventDate.editText?.inputType = InputType.TYPE_NULL
            tieEventDate.setOnClickListener {
                setEventDate()

            }
            tilEventTime.editText?.inputType = InputType.TYPE_NULL
            tieEventTime.setOnClickListener {
                setEventTime()
            }

        }
    }

    private fun addEvent() {
        with(binding) {

            viewModel.user.observe(this@AddEventActivity, { user ->
                val event = Event(
                    "",
                    tilEventName.editText?.text.toString(),
                    intent.getStringExtra(EVENT_TYPE).toString(),
                    tilEventCategory.editText?.text.toString(),
                    tilEventPrice.editText?.text.toString().toLong(),
                    DateConverter.convertStringToMillis(tilEventDate.editText?.text.toString()),
                    tilEventTime.editText?.text.toString(),
                    tilEventLocation.editText?.text.toString(),
                    tilLinkRegistration.editText?.text.toString(),
                    tilEventDescription.editText?.text.toString(),
                    tilEventPrerequisite.editText?.text.toString(),
                    "",
                    0,
                    0,
                    user.username,
                    user.uid
                )
                viewModel.addEvent(
                    event, viewModel.getImageUri().value!!
                ).observe(this@AddEventActivity, ::addEventResponse)
            })
        }

    }

    private fun addEventResponse(resource: Resource<Unit>) {
        when (resource) {
            is Resource.Success -> {
                loadingState(false)
                startActivity(Intent(this@AddEventActivity, MainActivity::class.java)).also {
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


    private fun pickImage() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY_PICK_REQUEST_CODE)
    }

    private fun isImageUriEmpty(): Boolean {
        viewModel.getImageUri().value.let {
            if (it != null) {
                return true
            }
        }
        return false
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
                isImageUriEmpty() -> {
                    return true
                }
                else -> {
                    return true
                }
            }
            return false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_PICK_REQUEST_CODE) {
                val imageUri = data?.data
                Glide.with(this).load(imageUri).transform(CenterCrop(), RoundedCorners(8))
                    .into(binding.ivAddCover)
                if (imageUri != null) {
                    viewModel.setImageUri(imageUri)
                }
            }
        }
    }

    private fun loadingState(state: Boolean) {
        with(binding) {
            progressBar.isVisible = state
            btnAddEvent.isEnabled = !state
        }
    }

    private fun setEventTime() {
        val pickerEventTimeBuilder =
            MaterialTimePicker
                .Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
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
            binding.tilEventDate.editText?.setText(DateConverter.convertMillisToString(it))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(eventModule)
    }

}
