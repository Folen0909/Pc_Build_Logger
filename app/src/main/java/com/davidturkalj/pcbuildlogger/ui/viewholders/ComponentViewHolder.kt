package com.davidturkalj.pcbuildlogger.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.databinding.ItemComponentBinding
import com.davidturkalj.pcbuildlogger.utilities.OnComponentClickListener
import com.squareup.picasso.Picasso

class ComponentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(component: Component, clickListener: OnComponentClickListener) {
        val itemBinding = ItemComponentBinding.bind(itemView)
        itemBinding.tvComponent.text = component.name
        itemBinding.btnDelete.setOnClickListener { clickListener.onComponentDelete(component) }
        Picasso.get().load(component.imageLink).placeholder(R.mipmap.ic_launcher).into(itemBinding.ivComponent)
    }
}