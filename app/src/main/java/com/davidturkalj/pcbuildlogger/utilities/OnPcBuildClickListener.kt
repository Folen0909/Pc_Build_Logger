package com.davidturkalj.pcbuildlogger.utilities

import com.davidturkalj.pcbuildlogger.data.model.PcBuild

interface OnPcBuildClickListener {
    fun onPcBuildDelete(pcBuild: PcBuild)
    fun onPcBuildDetails(pcBuild: PcBuild)
}