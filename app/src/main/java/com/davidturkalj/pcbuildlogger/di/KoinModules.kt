package com.davidturkalj.pcbuildlogger.di

import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.components.viewmodel.ComponentsViewModel
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.viewmodel.PcBuildViewModel
import com.davidturkalj.pcbuildlogger.ui.userManagement.viewmodel.UserManagementViewModel
import com.davidturkalj.pcbuildlogger.ui.tabs.user.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val componentModule = module {
    factory<Component> { Component() }
}
val pcBuildModule = module {
    factory<PcBuild> { PcBuild() }
}

val viewModelModule = module {
    viewModel<PcBuildViewModel> { PcBuildViewModel() }
    viewModel<UserManagementViewModel> { UserManagementViewModel() }
    viewModel<UserViewModel> { UserViewModel() }
    viewModel<ComponentsViewModel> { ComponentsViewModel() }
}