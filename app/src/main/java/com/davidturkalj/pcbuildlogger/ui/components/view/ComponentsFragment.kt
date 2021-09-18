package com.davidturkalj.pcbuildlogger.ui.components.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidturkalj.pcbuildlogger.databinding.FragmentComponentsBinding
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.ui.components.adapter.ComponentsAdapter
import com.davidturkalj.pcbuildlogger.ui.components.viewmodel.ComponentsViewModel
import com.davidturkalj.pcbuildlogger.utilities.DialogListener
import com.davidturkalj.pcbuildlogger.utilities.OnComponentClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComponentsFragment(private val pcBuildKey: String): Fragment(), OnComponentClickListener, DialogListener {

    private lateinit var componentsBinding: FragmentComponentsBinding
    private val viewModel by viewModel<ComponentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        componentsBinding = FragmentComponentsBinding.inflate(inflater, container, false)
        return componentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        componentsBinding.btnAddComponent.setOnClickListener { openNewComponentDialog() }
        viewModel.setBuildKey(pcBuildKey)
        viewModel.setUpDatabaseConnection()
        setUpRecyclerView()
        componentsBinding.btnAddComponent.setOnClickListener { openNewComponentDialog() }
        componentsBinding.btnBack.setOnClickListener { requireActivity().onBackPressed() }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setUpRecyclerView() {
        componentsBinding.rvComponents.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        componentsBinding.rvComponents.adapter = ComponentsAdapter(this)
        viewModel.components.observe(viewLifecycleOwner, Observer {
            (componentsBinding.rvComponents.adapter as ComponentsAdapter).refreshData(it)
        })
    }

    private fun openNewComponentDialog() {
        ComponentsNewDialog().show(parentFragmentManager, "New component", this)
    }

    override fun onComponentDelete(component: Component) {
        viewModel.deleteComponent(component)
    }

    override fun onDialogPositiveClick(name: String) {
        onDialogPositiveClick(name, "")
    }

    override fun onDialogPositiveClick(name: String, imageLink: String) {
        viewModel.createNewComponent(name, imageLink)
    }

    companion object {
        const val TAG = "Components"
        fun create(pcBuildKey: String): ComponentsFragment {
            return ComponentsFragment(pcBuildKey)
        }
    }
}