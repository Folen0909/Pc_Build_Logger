package com.davidturkalj.pcbuildlogger.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.databinding.ItemPcBuildBinding
import com.davidturkalj.pcbuildlogger.utilities.OnPcBuildClickListener

class PcBuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pcBuild: PcBuild, clickListener: OnPcBuildClickListener) {
        val itemBinding = ItemPcBuildBinding.bind(itemView)
        itemView.setOnClickListener { clickListener.onPcBuildDetails(pcBuild) }
        itemBinding.tvBuildName.text = pcBuild.name
        itemBinding.btnDelete.setOnClickListener { clickListener.onPcBuildDelete(pcBuild) }
    }
}