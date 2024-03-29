package com.lucassdalmeida.onemessagechat.domain.entities.chat

import java.util.UUID

class Chat(
    val id: String,
    message: String,
    subscribers: Set<UUID>,
) {
    var message: String = ""
        set(value) {
            require(value.isNotBlank()) { "The message cannot be an blank string!" }
            field = value
        }

    private val _subscribers = subscribers.toMutableSet()
    val subscribers get() = _subscribers.toSet()

    init {
        require(id.isNotBlank()) { "The id cannot be an blank string!" }
        this.message = message
    }

    fun subscribe(subscriberId: UUID) = _subscribers.add(subscriberId)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chat
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "Chat(id='$id', message='$message')"
}