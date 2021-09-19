package com.davidturkalj.pcbuildlogger.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidturkalj.pcbuildlogger.databinding.FragmentRegisterBinding
import com.davidturkalj.pcbuildlogger.ui.viewmodels.SignInViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment : Fragment() {

    private val viewModel by sharedViewModel<SignInViewModel>()
    private var signInListener : (() -> Unit)? = null

    private lateinit var binding: FragmentRegisterBinding

    fun setUpSignInListener(listener: (() -> Unit)){
        this.signInListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLogin.setOnClickListener{
            signInListener?.invoke()
        }
        binding.btnRegister.setOnClickListener{
            viewModel.register(binding.etUserEmail.text.toString(), binding.etUserPassword.text.toString())
        }
    }
}