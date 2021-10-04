package com.maungedev.eventtechorganizer.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.maungedev.domain.model.Event
import com.maungedev.eventtechorganizer.databinding.ItemEventBinding
import com.maungedev.eventtechorganizer.databinding.ItemInsightBinding

class EventInsightAdapter(private val context: Context) :
    RecyclerView.Adapter<EventInsightAdapter.ViewHolder>() {

    private val events = arrayListOf<Event>()

    fun setItems(items: List<Event>) {
        this.events.clear()
        this.events.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInsightAdapter.ViewHolder {
        val itemBinding =
            ItemInsightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EventInsightAdapter.ViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount() = events.size

    inner class ViewHolder(private val binding: ItemInsightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Event) {
            with(binding) {
                tvEventCategory.text = "Kategori: ${item.eventCategory}"
                tvEventTitle.text = item.eventName
                tvNumberOfSeen.text = item.numbersOfView.toString()
                tvNumberOfOpened.text = item.numbersOfRegistrationClick.toString()

            }
        }
    }
}