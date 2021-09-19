package com.davidturkalj.pcbuildlogger.utilities

import com.davidturkalj.pcbuildlogger.data.model.Component

interface OnComponentClickListener {
    fun onComponentDelete(component: Component)
}
