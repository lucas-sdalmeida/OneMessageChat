package com.lucassdalmeida.onemessagechat.domain.application.chat.create

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat

class CreateChatService(
    private val chatRepository: ChatRepository,
) {
    fun create(id: String, initialMessage: String) {
        val chat = Chat(id, initialMessage)
        chatRepository.create(chat)
    }
}