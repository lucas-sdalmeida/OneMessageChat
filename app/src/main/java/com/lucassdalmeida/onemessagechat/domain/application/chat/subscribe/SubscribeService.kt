package com.lucassdalmeida.onemessagechat.domain.application.chat.subscribe

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import java.util.UUID

class SubscribeService(
    private val chatRepository: ChatRepository,
    private val subscriberRepository: SubscriberRepository,
) {
    fun subscribe(id: String, subscriberId: UUID) {
        if (subscriberId !in subscriberRepository)
            throw NoSuchElementException("There is not a subscriber with id: $subscriberId")
        val chat = chatRepository.findById(id) ?:
            throw NoSuchElementException("Theres is not a chat with id: $id")

        chat.subscribe(subscriberId)
        chatRepository.create(chat)
    }
}