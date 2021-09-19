package com.davidturkalj.pcbuildlogger.utilities

import com.davidturkalj.pcbuildlogger.data.model.PcBuild

interface PcBuildsCallback {
     fun getPcBuildsCallback(pcBuilds: MutableList<PcBuild>)
}