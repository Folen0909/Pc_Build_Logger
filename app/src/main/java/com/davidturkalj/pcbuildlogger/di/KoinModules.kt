package com.davidturkalj.pcbuildlogger.di

import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.data.repository.Repository
import com.davidturkalj.pcbuildlogger.ui.viewmodels.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single<Repository> { Repository() }
    factory<Component> { Component() }
    factory<PcBuild> { PcBuild() }
}

val viewModelModule = module {
    viewModel<PcBuildViewModel> { PcBuildViewModel(get()) }
    viewModel<SignInViewModel> { SignInViewModel(get()) }
    viewModel<ComponentsViewModel> { ComponentsViewModel(get()) }
    viewModel<TabsViewModel> { TabsViewModel(get()) }
}