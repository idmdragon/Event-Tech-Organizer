package com.maungedev.eventtechorganizer.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.maungedev.domain.model.Event
import com.maungedev.eventtechorganizer.R
import com.maungedev.eventtechorganizer.constant.ExtraNameConstant
import com.maungedev.eventtechorganizer.constant.PageNameConstant.EDIT_EVENT_PAGE
import com.maungedev.eventtechorganizer.databinding.ItemEventBinding
import com.maungedev.eventtechorganizer.utils.DateConverter

class EventLayoutAdapter(private val context: Context) :
    RecyclerView.Adapter<EventLayoutAdapter.ViewHolder>() {

    private val events = arrayListOf<Event>()

    var onItemClick: ((Event) -> Unit)? = null


    fun setItems(items: List<Event>) {
        this.events.clear()
        this.events.addAll(items)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventLayoutAdapter.ViewHolder {
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
                tvEventDate.text = DateConverter.convertMillisToString(item.date)
                Glide.with(itemView.context)
                    .load(item.eventCover)
                    .transform(CenterCrop(), RoundedCorners(8))
                    .placeholder(ColorDrawable(Color.CYAN))
                    .apply(RequestOptions())
                    .into(ivPoster)

                btnMenu.setOnClickListener {
                    showMenu(it, R.menu.event_menu, itemView.context, item)

                }
            }
        }


        private fun showMenu(
            view: View,
            optionMenu: Int,
            context: Context,
            event: Event,
        ) {
            val popup = PopupMenu(context, view)
            popup.menuInflater.inflate(optionMenu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->

                if (menuItem.itemId == R.id.edit_event) {
                    context.startActivity(
                        Intent(itemView.context, Class.forName(EDIT_EVENT_PAGE)).putExtra(
                            ExtraNameConstant.EVENT_UID, event.uid
                        )
                    )
                } else if (menuItem.itemId == R.id.delete_event) {
                    val materialBuilder = MaterialAlertDialogBuilder(itemView.context).create()
                    val inflater: View =
                        LayoutInflater.from(context).inflate(R.layout.dialog_delete, null)
                    val btnDelete: Button = inflater.findViewById(R.id.btn_accept)
                    val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)
                    val deleteDescription: TextView = inflater.findViewById(R.id.tv_desc)

                    deleteDescription.text =
                        context.getString(R.string.desc_dialog_delete, event.eventName)

                    btnDelete.setOnClickListener {
                        onItemClick?.invoke(event)
                        materialBuilder.dismiss()
                    }
                    btnCancel.setOnClickListener {
                        materialBuilder.dismiss()
                    }

                    materialBuilder.setView(inflater)
                    materialBuilder.show()
                }
                true
            }
            popup.setOnDismissListener {}
            popup.show()
        }


    }
}

