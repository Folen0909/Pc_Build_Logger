package com.davidturkalj.pcbuildlogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidturkalj.pcbuildlogger.databinding.FragmentLoginBinding
import com.davidturkalj.pcbuildlogger.ui.viewmodels.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment : Fragment() {

    private val viewModel by sharedViewModel<SignInViewModel>()
    private var registerListener : (() -> Unit)? = null

    private lateinit var binding: FragmentLoginBinding

    fun setUpRegisterListener(listener: (() -> Unit)){
        this.registerListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.tvRegister.setOnClickListener{
            registerListener?.invoke()
        }
        binding.btnLogin.setOnClickListener{
            viewModel.signIn(binding.etUserEmail.text.toString(), binding.etUserPassword.text.toString())
        }
    }
}