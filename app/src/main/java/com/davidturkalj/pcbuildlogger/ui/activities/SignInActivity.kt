package com.davidturkalj.pcbuildlogger.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidturkalj.pcbuildlogger.PCBuildLogger
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.databinding.ActivitySignInBinding
import com.davidturkalj.pcbuildlogger.ui.fragments.LoginFragment
import com.davidturkalj.pcbuildlogger.ui.fragments.RegisterFragment
import com.davidturkalj.pcbuildlogger.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val TAG = "SignInActivity"

    private val viewModel by viewModel<SignInViewModel>()
    private val loginFragment: LoginFragment = LoginFragment()
    private val registerFragment: RegisterFragment = RegisterFragment()

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)

        registerFragment.setUpSignInListener {
            supportFragmentManager.beginTransaction().replace(R.id.fcv_sign_in,loginFragment).commit()
        }
        loginFragment.setUpRegisterListener {
            supportFragmentManager.beginTransaction().replace(R.id.fcv_sign_in, registerFragment).commit()
        }

        viewModel.isUserRegisteredSuccessfully.observe(this){
            if(it){
                startMainActivity()
            }
        }
        viewModel.isSigningInSuccessful.observe(this){
            if(it){
                startMainActivity()
            }
        }
        viewModel.isUserSignedIn.observe(this){
            if(it == true){
                startMainActivity()
            } else if(it == false){
                supportFragmentManager.beginTransaction().replace(R.id.fcv_sign_in, loginFragment).commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.checkIfSignedIn()
    }

    private fun startMainActivity(){
        CoroutineScope(Dispatchers.IO).launch {
            val intent: Intent = Intent(PCBuildLogger.ApplicationContext, TabsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK  or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }


}