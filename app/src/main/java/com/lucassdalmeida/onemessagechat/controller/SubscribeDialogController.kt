package com.lucassdalmeida.onemessagechat.controller

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import com.lucassdalmeida.onemessagechat.databinding.SubscribeDialogBinding
import com.lucassdalmeida.onemessagechat.domain.application.chat.subscribe.SubscribeService
import com.lucassdalmeida.onemessagechat.persistence.firebase.chat.RealtimeChatRepository
import com.lucassdalmeida.onemessagechat.persistence.firebase.subscriber.RealtimeSubscriberRepository
import com.lucassdalmeida.onemessagechat.view.SubscribeDialog
import java.util.UUID

class SubscribeDialogController(
    private val context: Context,
    private val subscriberId: UUID,
    private val subscribeDialog: SubscribeDialog,
    private val subscribeDialogBinding: SubscribeDialogBinding,
) {
    private val chatRepository = RealtimeChatRepository()
    private val subscriberRepository = RealtimeSubscriberRepository()
    private val subscribeService = SubscribeService(chatRepository, subscriberRepository)

    init {
        with(subscribeDialogBinding) {
            subscribe.setOnClickListener {
                subscribe()
            }
        }
    }

    private fun subscribe() {
        val chatId = subscribeDialogBinding.subscribingChatId.text.toString()

        try {
            subscribeService.subscribe(chatId, subscriberId)
            subscribeDialog.dismiss()
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}