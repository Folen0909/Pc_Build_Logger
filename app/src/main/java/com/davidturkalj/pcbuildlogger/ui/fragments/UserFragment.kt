package com.davidturkalj.pcbuildlogger.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.davidturkalj.pcbuildlogger.databinding.FragmentUserBinding
import com.davidturkalj.pcbuildlogger.ui.activities.SignInActivity
import com.davidturkalj.pcbuildlogger.ui.viewmodels.TabsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UserFragment : Fragment() {

    private val viewModel by sharedViewModel<TabsViewModel>()
    lateinit var userBinding: FragmentUserBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        userBinding = FragmentUserBinding.inflate(inflater, container, false)
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateUser()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            userBinding.tvUserEmail.text = it.email
            userBinding.tvUserId.text = it.uid
        })
        userBinding.btnUserLogout.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.signOut()
                startSignInActivity()
            }
        }
    }

    private fun startSignInActivity(){
        CoroutineScope(Dispatchers.IO).launch {
            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "User"
        fun create(): UserFragment {
            return UserFragment()
        }
    }
}