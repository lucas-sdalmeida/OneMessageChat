package com.lucassdalmeida.onemessagechat.domain.application.chat.edit

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import java.util.UUID

class EditMessageService(
    private val chatRepository: ChatRepository,
    private val subscriberRepository: SubscriberRepository,
) {
    fun edit(id: String, subscriberId: UUID, message: String) {
        if (subscriberId !in subscriberRepository)
            throw NoSuchElementException("There is not a subscriber with id: $subscriberId")

        val chat = chatRepository.findById(id) ?:
            throw NoSuchElementException("There is not a chat with id: $id")

        if (subscriberId !in chat.subscribers)
            throw IllegalStateException("The chat $id has not a subscriber with id: $subscriberId")

        chat.message = message
        chatRepository.create(chat)
    }
}