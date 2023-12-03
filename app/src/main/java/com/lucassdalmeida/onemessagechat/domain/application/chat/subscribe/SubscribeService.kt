package com.lucassdalmeida.onemessagechat.domain.application.chat.subscribe

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository

class SubscribeService(
    private val chatRepository: ChatRepository,
) {
    fun subscribe(id: String) {
        chatRepository.findById(id)?.let { chatRepository.create(it) } ?:
            throw NoSuchElementException("Theres is not a chat with id: $id")
    }
}