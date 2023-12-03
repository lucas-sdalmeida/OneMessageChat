package com.lucassdalmeida.onemessagechat.domain.entities.chat

class Chat(
    val id: String,
    message: String,
) {
    var message: String = ""
        set(value) {
            require(value.isNotBlank()) { "The message cannot be an blank string!" }
            field = value
        }

    init {
        require(id.isNotBlank()) { "The id cannot be an blank string!" }
        this.message = message
    }

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