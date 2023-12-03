package com.lucassdalmeida.onemessagechat.domain.application.chat.create

import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import java.util.UUID

class CreateChatService(
    private val chatRepository: ChatRepository,
    private val subscriberRepository: SubscriberRepository,
) {
    fun create(id: String, creator: UUID, initialMessage: String) {
        if (creator !in subscriberRepository)
            throw NoSuchElementException("There is not a subscriber with id: $id")
        val chat = Chat(id, initialMessage, setOf(creator))
        chatRepository.create(chat)
    }
}