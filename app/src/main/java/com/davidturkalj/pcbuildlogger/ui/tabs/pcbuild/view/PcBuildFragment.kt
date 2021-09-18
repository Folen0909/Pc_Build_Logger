package com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.databinding.FragmentPcBuildBinding
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.ui.components.view.ComponentsFragment
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.adapter.PcBuildAdapter
import com.davidturkalj.pcbuildlogger.ui.tabs.pcbuild.viewmodel.PcBuildViewModel
import com.davidturkalj.pcbuildlogger.utilities.DialogListener
import com.davidturkalj.pcbuildlogger.utilities.OnPcBuildClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PcBuildFragment: Fragment(), OnPcBuildClickListener, DialogListener {

    private lateinit var pcBuildBinding: FragmentPcBuildBinding
    private val viewModel by viewModel<PcBuildViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pcBuildBinding = FragmentPcBuildBinding.inflate(inflater, container, false)
        return pcBuildBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setUpDatabaseConnection()
        pcBuildBinding.btnAddBuild.setOnClickListener { openNewPcBuildDialog() }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        pcBuildBinding.rvBuilds.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        pcBuildBinding.rvBuilds.adapter = PcBuildAdapter(this)
        viewModel.builds.observe(viewLifecycleOwner, Observer {
            (pcBuildBinding.rvBuilds.adapter as PcBuildAdapter).refreshData(it)
        })
    }

    private fun openNewPcBuildDialog() {
        PcBuildNewDialog().show(parentFragmentManager, "New build", this)
    }

    override fun onPcBuildDelete(pcBuild: PcBuild) {
        viewModel.deletePcBuild(pcBuild)
    }

    override fun onPcBuildDetails(pcBuild: PcBuild) {
        Toast.makeText(context, pcBuild.name, Toast.LENGTH_SHORT).show()
        val key = pcBuild.key!!
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_pc_build, ComponentsFragment.create(key))
            .addToBackStack(null)
            .commit()
    }

    override fun onDialogPositiveClick(name: String) {
        viewModel.createNewBuild(name)
    }

    override fun onDialogPositiveClick(name: String, imageLink: String) {
        viewModel.key?.let { ComponentsFragment(it).onDialogPositiveClick(name, imageLink) }
    }

    companion object {
        const val TAG = "PC Build"
        fun create(): PcBuildFragment {
            return PcBuildFragment()
        }
    }



}