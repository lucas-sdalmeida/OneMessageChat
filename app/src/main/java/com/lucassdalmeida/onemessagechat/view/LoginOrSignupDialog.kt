package com.lucassdalmeida.onemessagechat.view

import android.app.Dialog
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.DialogInterface.OnDismissListener
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.controller.LoginOrSignupDialogController
import com.lucassdalmeida.onemessagechat.databinding.LoginDialogBinding

class LoginOrSignupDialog(
    context: Context,
    onDismissListener: LoginOrSignupDialog.() -> Unit
) {
    private val loginDialogBinding = context.getSystemService(LAYOUT_INFLATER_SERVICE).let {
        LoginDialogBinding.inflate(it as LayoutInflater)
    }
    private val innerDialog = Dialog(context).also {
        it.setContentView(loginDialogBinding.root)
        it.setCancelable(false)
        it.setCanceledOnTouchOutside(false)
        it.setOnDismissListener { onDismissListener() }
        it.show()
    }
    private val controller = LoginOrSignupDialogController(context, this, loginDialogBinding)
    val subscriberId get() = controller.subscriberId

    fun dismiss() = innerDialog.dismiss()
}