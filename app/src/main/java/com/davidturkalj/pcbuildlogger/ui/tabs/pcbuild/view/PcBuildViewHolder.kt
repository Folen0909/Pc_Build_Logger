package com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.databinding.ItemPcBuildBinding
import com.davidturkalj.pcbuildlogger.utilities.OnPcBuildClickListener
import com.davidturkalj.pcbuildlogger.data.model.PcBuild

class PcBuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pcBuild: PcBuild, clickListener: OnPcBuildClickListener) {
        val itemBinding = ItemPcBuildBinding.bind(itemView)
        itemView.setOnClickListener { clickListener.onPcBuildDetails(pcBuild) }
        itemBinding.tvBuildName.text = pcBuild.name
        itemBinding.btnDelete.setOnClickListener { clickListener.onPcBuildDelete(pcBuild) }
    }
}