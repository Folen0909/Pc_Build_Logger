package com.davidturkalj.pcbuildlogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.data.model.PcBuild
import com.davidturkalj.pcbuildlogger.databinding.FragmentPcBuildBinding
import com.davidturkalj.pcbuildlogger.ui.activities.TabsActivity
import com.davidturkalj.pcbuildlogger.ui.adapters.PcBuildAdapter
import com.davidturkalj.pcbuildlogger.ui.viewmodels.PcBuildViewModel
import com.davidturkalj.pcbuildlogger.utilities.OnPcBuildClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class PcBuildFragment: Fragment(), OnPcBuildClickListener {

    private lateinit var pcBuildBinding: FragmentPcBuildBinding
    private val viewModel by viewModel<PcBuildViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pcBuildBinding = FragmentPcBuildBinding.inflate(inflater, container, false)
        return pcBuildBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPcBuilds()
        pcBuildBinding.btnAddBuild.setOnClickListener {
            viewModel.openDialog(activity as TabsActivity)
        }
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        pcBuildBinding.rvBuilds.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        pcBuildBinding.rvBuilds.adapter = PcBuildAdapter(this)
        viewModel.pcBuilds.observe(viewLifecycleOwner, Observer {
            (pcBuildBinding.rvBuilds.adapter as PcBuildAdapter).refreshData(it)
        })
    }

    override fun onPcBuildDelete(pcBuild: PcBuild) {
        viewModel.deletePcBuild(pcBuild)
    }

    override fun onPcBuildDetails(pcBuild: PcBuild) {
        Toast.makeText(context, pcBuild.name, Toast.LENGTH_SHORT).show()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fl_pc_build, ComponentsFragment.create(pcBuild.key))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val TAG = "PC Build"
        fun create(): PcBuildFragment {
            return PcBuildFragment()
        }
    }



}