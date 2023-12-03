package com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository

import com.lucassdalmeida.onemessagechat.domain.entities.subscriber.Subscriber
import java.util.UUID

interface SubscriberRepository {
    fun create(subscriber: Subscriber)

    fun findById(id: UUID): Subscriber?

    fun findByUsername(username: String): Subscriber?

    operator fun contains(id: UUID): Boolean

    operator fun contains(username: String): Boolean
}