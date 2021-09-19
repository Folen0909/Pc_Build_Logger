package com.davidturkalj.pcbuildlogger.utilities

import com.davidturkalj.pcbuildlogger.data.model.Component

interface ComponentsCallback {
    fun getComponentsCallback(components: MutableList<Component>)
}