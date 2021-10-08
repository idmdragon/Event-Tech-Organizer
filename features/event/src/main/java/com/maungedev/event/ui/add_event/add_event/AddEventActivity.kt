package com.maungedev.event.ui.add_event.add_event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.maungedev.domain.model.Event
import com.maungedev.domain.utils.Resource
import com.maungedev.event.databinding.ActivityAddEventBinding
import com.maungedev.event.di.eventModule
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant.EVENT_TYPE
import com.maungedev.eventtechorganizer.main.MainActivity
import org.koin.core.context.loadKoinModules
import java.net.URI
import org.koin.android.viewmodel.ext.android.viewModel

class AddEventActivity : AppCompatActivity() {

    companion object {
        const val GALLERY_PICK_REQUEST_CODE = 200
    }

    private lateinit var binding: ActivityAddEventBinding
    private val viewModel: AddEventViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(eventModule)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setView()


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
                if (isEventNotEmpty()){
                    addEvent()
                }
            }
        }

    }

    private fun addEvent() {
        with(binding) {

            val event = Event(
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
                "",
                0,
                0,
                listOf(),
                "Hology UB"
            )
            Log.d("AddEventDEBUGING","addEvent() isi Uri $event")
            viewModel.addEvent(
                event
             , viewModel.getImageUri().value!!
            ).observe(this@AddEventActivity,::addEventResponse)
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
            Log.d("AddEventDEBUGING","isImageUriEmpty() isi Uri $it")
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
                Log.d("AddEventDEBUGING","onActivityResult() isi Uri $imageUri")
                Glide.with(this).load(imageUri).into(binding.ivAddCover)
                if (imageUri != null) {
                    viewModel.setImageUri(imageUri)
                }
            }
        }
    }

    private fun loadingState(state: Boolean) {
        with(binding){
            progressBar.isVisible = state
            btnAddEvent.isEnabled = !state
        }
    }

}