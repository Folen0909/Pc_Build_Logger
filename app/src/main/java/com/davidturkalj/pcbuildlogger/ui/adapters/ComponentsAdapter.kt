package com.davidturkalj.pcbuildlogger.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.ui.viewholders.ComponentViewHolder
import com.davidturkalj.pcbuildlogger.utilities.OnComponentClickListener

class ComponentsAdapter(private val clickListener: OnComponentClickListener)
    : RecyclerView.Adapter<ComponentViewHolder>() {

    private val components: MutableList<Component> = mutableListOf()

    fun refreshData(components: List<Component>) {
        this.components.clear()
        this.components.addAll(components)
        this.notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComponentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_component, parent,false)
        return ComponentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {
        val component = components[position]
        holder.bind(component, clickListener)
    }

    override fun getItemCount(): Int {
        return components.size
    }

}