package com.davidturkalj.pcbuildlogger.ui.components.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.utilities.OnComponentClickListener
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.databinding.ItemComponentBinding

class ComponentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(component: Component, clickListener: OnComponentClickListener) {
        val itemBinding = ItemComponentBinding.bind(itemView)
        itemBinding.tvComponent.text = component.name
        itemBinding.btnDelete.setOnClickListener { clickListener.onComponentDelete(component) }
    }
}