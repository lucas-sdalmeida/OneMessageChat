package com.lucassdalmeida.onemessagechat.domain.application.subcriber.login

import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import java.util.UUID

class LoginService(
    private val subscriberRepository: SubscriberRepository,
) {
    fun login(username: String, password: String): UUID {
        val subscriber = subscriberRepository.findByUsername(username) ?:
            throw NoSuchElementException("There is not a subscriber with username: $username")

        if (subscriber.password != password.hashCode())
            throw IllegalArgumentException("Wrong password for $username")

        return subscriber.id
    }
}