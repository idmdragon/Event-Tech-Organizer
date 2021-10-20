package com.maungedev.eventtechorganizer.adapter

import android.content.Context
import android.content.Intent
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
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant
import com.maungedev.eventtechorganizer.constant.PageNameConstant.EDIT_EVENT_PAGE
import com.maungedev.eventtechorganizer.databinding.ItemEventBinding

class EventLayoutAdapter(private val context: Context) :
    RecyclerView.Adapter<EventLayoutAdapter.ViewHolder>() {

    private val events = arrayListOf<Event>()

    fun setItems(items: List<Event>) {
        this.events.clear()
        this.events.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventLayoutAdapter.ViewHolder {
        val itemBinding =
            ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: EventLayoutAdapter.ViewHolder, position: Int) {
        holder.bind(events[position])
        
    }

    override fun getItemCount() = events.size

    inner class ViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Event) {
            with(binding) {
                tvEventCategory.text = item.eventCategory
                tvEventTitle.text = item.eventName
                tvEventDate.text = item.date
                Glide.with(itemView.context)
                    .load(item.eventCover)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .placeholder(ColorDrawable(Color.CYAN))
                    .apply(RequestOptions())
                    .into(ivPoster)

                itemView.setOnClickListener {
                    context.startActivity(Intent(itemView.context,Class.forName(EDIT_EVENT_PAGE)).putExtra(
                        ExtraNameConstant.EVENT_UID,item.uid))
                }
            }
        }
    }
}