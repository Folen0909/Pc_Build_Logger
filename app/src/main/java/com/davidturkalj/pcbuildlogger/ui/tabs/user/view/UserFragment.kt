package com.davidturkalj.pcbuildlogger.ui.tabs.user.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.davidturkalj.pcbuildlogger.databinding.FragmentUserBinding
import com.davidturkalj.pcbuildlogger.ui.tabs.user.viewmodel.UserViewModel
import com.davidturkalj.pcbuildlogger.ui.userManagement.view.UserManagementActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFragment : Fragment() {

    private val viewModel by viewModel<UserViewModel>()
    lateinit var userBinding: FragmentUserBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        userBinding = FragmentUserBinding.inflate(inflater, container, false)
        
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setData(
            requireActivity().intent.getStringExtra("email"),
            requireActivity().intent.getStringExtra("user_id")
        )
        viewModel.email.observe(viewLifecycleOwner, Observer {
            userBinding.tvUserEmail.text = it
        })
        viewModel.userId.observe(viewLifecycleOwner, Observer {
            userBinding.tvUserId.text = it
        })
        viewModel.isLogout.observe(viewLifecycleOwner, Observer {
            this.logout()
        })
        userBinding.btnUserLogout.setOnClickListener { viewModel.logout() }

    }

    private fun logout() {
        requireActivity().startActivity(Intent(activity, UserManagementActivity::class.java))
        requireActivity().finish()
    }

    companion object {
        const val TAG = "User"
        fun create(): UserFragment {
            return UserFragment()
        }
    }
}