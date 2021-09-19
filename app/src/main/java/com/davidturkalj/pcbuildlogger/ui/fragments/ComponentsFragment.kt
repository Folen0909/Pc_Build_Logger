package com.davidturkalj.pcbuildlogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidturkalj.pcbuildlogger.data.model.Component
import com.davidturkalj.pcbuildlogger.databinding.FragmentComponentsBinding
import com.davidturkalj.pcbuildlogger.ui.activities.TabsActivity
import com.davidturkalj.pcbuildlogger.ui.adapters.ComponentsAdapter
import com.davidturkalj.pcbuildlogger.ui.viewmodels.ComponentsViewModel
import com.davidturkalj.pcbuildlogger.utilities.OnComponentClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComponentsFragment(private val key: String?) : Fragment(), OnComponentClickListener {

    private lateinit var componentsBinding: FragmentComponentsBinding
    private val viewModel by viewModel<ComponentsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        componentsBinding = FragmentComponentsBinding.inflate(inflater, container, false)
        return componentsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setComponentReference(key)
        viewModel.getComponents()

        componentsBinding.btnAddComponent.setOnClickListener {
            viewModel.openDialog(activity as TabsActivity)
        }
        setUpRecyclerView()
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

    override fun onComponentDelete(component: Component) {
        viewModel.deleteComponent(component)
    }

    companion object {
        const val TAG = "Components"
        fun create(key: String?): ComponentsFragment {
            return ComponentsFragment(key)
        }
    }
}