package com.lucassdalmeida.onemessagechat.view

import android.app.Dialog
import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import androidx.core.content.getSystemService
import com.lucassdalmeida.onemessagechat.controller.SubscribeDialogController
import com.lucassdalmeida.onemessagechat.databinding.SubscribeDialogBinding
import java.util.UUID

class SubscribeDialog(
    context: Context,
    subscriberId: UUID,
    private val onDismiss: SubscribeDialog.() -> Unit,
) {
    private val subscribeDialogBinding = SubscribeDialogBinding.inflate(
        context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
    )
    private val innerDialog = Dialog(context).also {
        it.setContentView(subscribeDialogBinding.root)
        it.setCancelable(false)
        it.setOnDismissListener { onDismiss() }
        it.show()
    }
    private val controller = SubscribeDialogController(
        context,
        subscriberId,
        this,
        subscribeDialogBinding
    )

    fun dismiss() = innerDialog.dismiss()
}