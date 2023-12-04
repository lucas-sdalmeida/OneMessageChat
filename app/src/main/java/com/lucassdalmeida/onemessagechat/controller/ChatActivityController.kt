package com.lucassdalmeida.onemessagechat.controller

import android.widget.Toast
import com.lucassdalmeida.onemessagechat.databinding.ActivityChatBinding
import com.lucassdalmeida.onemessagechat.domain.application.chat.create.CreateChatService
import com.lucassdalmeida.onemessagechat.persistence.firebase.chat.RealtimeChatRepository
import com.lucassdalmeida.onemessagechat.persistence.firebase.subscriber.RealtimeSubscriberRepository
import com.lucassdalmeida.onemessagechat.view.ChatActivity
import java.util.UUID

class ChatActivityController(
    private val subscriberId: UUID,
    private val chatActivity: ChatActivity,
    private val chatActivityBinding: ActivityChatBinding,
) {
    private val realtimeChatRepository = RealtimeChatRepository()
    private val realtimeSubscriberRepository = RealtimeSubscriberRepository()

    init {
        with(chatActivityBinding) {
            saveMessageButton.setOnClickListener {
                createChat()
            }
        }
    }

    private fun createChat() {
        val chatId = chatActivityBinding.chatIdField.text.toString()
        val message = chatActivityBinding.messageField.text.toString()
        val createChatService = CreateChatService(realtimeChatRepository, realtimeSubscriberRepository)

        try {
            createChatService.create(chatId, subscriberId, message)
            chatActivity.finish()
        }
        catch (e: Exception) {
            Toast.makeText(chatActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}