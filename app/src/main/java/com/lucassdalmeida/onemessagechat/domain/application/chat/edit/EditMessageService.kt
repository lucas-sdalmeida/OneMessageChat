package com.lucassdalmeida.onemessagechat.domain.application.chat.edit

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository

class EditMessageService(
    private val chatRepository: ChatRepository,
) {
    fun edit(id: String, message: String) {
        val chat = chatRepository.findById(id) ?:
            throw NoSuchElementException("There is not a chat with id: $id")

        chat.message = message
        chatRepository.create(chat)
    }
}