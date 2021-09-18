package com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view.PcBuildViewHolder
import com.davidturkalj.pcbuildlogger.utilities.OnPcBuildClickListener

class PcBuildAdapter(private val clickListener: OnPcBuildClickListener)
    : RecyclerView.Adapter<PcBuildViewHolder>() {

    private val pcBuilds: MutableList<PcBuild> = mutableListOf()

    fun refreshData(pcBuilds: List<PcBuild>) {
        this.pcBuilds.clear()
        this.pcBuilds.addAll(pcBuilds)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PcBuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pc_build, parent, false)
        return PcBuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: PcBuildViewHolder, position: Int) {
        val pcBuild = pcBuilds[position]
        holder.bind(pcBuild, clickListener)
    }

    override fun getItemCount(): Int {
        return pcBuilds.size
    }
}