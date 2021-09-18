package com.davidturkalj.pcbuildlogger.ui.userManagement.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidturkalj.pcbuildlogger.R
import com.davidturkalj.pcbuildlogger.databinding.ActivityUserManagementBinding
import com.davidturkalj.pcbuildlogger.ui.tabs.TabsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.davidturkalj.pcbuildlogger.ui.userManagement.viewmodel.UserManagementViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserManagementActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserManagementViewModel>()
    lateinit var userManagementBinding: ActivityUserManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userManagementBinding = ActivityUserManagementBinding.inflate(layoutInflater)
        userManagementBinding.tvRegister.setOnClickListener { viewModel.switchType() }

        viewModel.title.observe(this, { type ->
            userManagementBinding.tvTitle.text = type
            if ( type == getString(R.string.user_login)) {
                userManagementBinding.btnUserAction.setOnClickListener {
                    val email = userManagementBinding.etUserEmail.text.toString().trim {it <= ' '}
                    val password = userManagementBinding.etUserPassword.text.toString().trim { it <= ' ' }
                    viewModel.loginUser(email, password)
                }
            } else {
                userManagementBinding.btnUserAction.setOnClickListener {
                    val email = userManagementBinding.etUserEmail.text.toString().trim {it <= ' '}
                    val password = userManagementBinding.etUserPassword.text.toString().trim { it <= ' ' }
                    viewModel.registerUser(email, password)
                }
            }
        })
        viewModel.newUser.observe(this, {
            userManagementBinding.tvNewUser.text = it
        })
        viewModel.register.observe(this, {
            userManagementBinding.tvRegister.text = it
        })
        viewModel.userAction.observe(this, {
            userManagementBinding.btnUserAction.text = it
        })
        viewModel.firebaseUser.observe(this,  {
            user -> startNewIntent(user)
        })
        viewModel.postText.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        setContentView(userManagementBinding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            startNewIntent(user)
        }
    }

    private fun startNewIntent(firebaseUser: FirebaseUser) {
        val intent = Intent(this@UserManagementActivity, TabsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("user_id", firebaseUser.uid)
        intent.putExtra("email", firebaseUser.email)
        startActivity(intent)
        finish()
    }

}