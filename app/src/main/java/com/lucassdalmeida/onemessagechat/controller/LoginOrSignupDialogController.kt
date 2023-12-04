package com.lucassdalmeida.onemessagechat.controller

import android.content.Context
import android.widget.Toast
import androidx.core.view.isVisible
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.databinding.LoginDialogBinding
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.login.LoginService
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.signup.SignUpService
import com.lucassdalmeida.onemessagechat.persistence.firebase.subscriber.RealtimeSubscriberRepository
import com.lucassdalmeida.onemessagechat.view.LoginOrSignupDialog
import java.util.UUID

class LoginOrSignupDialogController(
    private val context: Context,
    private val dialog: LoginOrSignupDialog,
    private val loginDialogBinding: LoginDialogBinding,
) {
    lateinit var subscriberId: UUID
    private val realtimeSubscriberRepository = RealtimeSubscriberRepository()

    init {
        with (loginDialogBinding) {
            loginOrSignupButton.setOnClickListener {
                login()
            }
            toogleToSignup.setOnClickListener {
                it.isVisible = false
                loginOrSignupButton.text = context.getString(R.string.signup_button)
                loginOrSignupButton.setOnClickListener {
                    signup()
                }
            }
        }
    }

    private fun login() {
        val username = loginDialogBinding.usernameLoginField.text.toString()
        val password = loginDialogBinding.passwordLoginField.text.toString()
        val loginService = LoginService(realtimeSubscriberRepository)

        try {
            subscriberId = loginService.login(username, password)
            dialog.dismiss()
        }
        catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun signup() {
        val username = loginDialogBinding.usernameLoginField.text.toString()
        val password = loginDialogBinding.passwordLoginField.text.toString()
        val signUpService = SignUpService(realtimeSubscriberRepository)

        try {
            subscriberId = signUpService.signup(username, password)
            dialog.dismiss()
        }
        catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}