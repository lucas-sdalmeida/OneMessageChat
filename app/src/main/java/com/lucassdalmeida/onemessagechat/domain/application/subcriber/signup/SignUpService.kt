package com.lucassdalmeida.onemessagechat.domain.application.subcriber.signup

import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import com.lucassdalmeida.onemessagechat.domain.entities.subscriber.Subscriber
import java.util.UUID

class SignUpService(
    private val subscriberRepository: SubscriberRepository,
) {
    fun signup(username: String, password: String): UUID {
        check(username !in subscriberRepository) {
            "There already is a subscriber with username: $username"
        }

        val id = UUID.randomUUID()
        val subscriber = Subscriber(id, username, password)

        subscriberRepository.create(subscriber)
        return id
    }
}