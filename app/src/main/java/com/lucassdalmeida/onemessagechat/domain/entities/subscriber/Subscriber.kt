package com.lucassdalmeida.onemessagechat.domain.entities.subscriber

import java.util.UUID

class Subscriber(
    val id: UUID,
    val username: String,
    val password: Int,
) {
    constructor(
        id: UUID,
        username: String,
        password: String,
    ): this(id, username, password.hashCode()) {
        require(password.isNotBlank()) { "The password cannot be blank!" }
    }

    init {
        require(username.isNotBlank()) { "The username cannot be blank!" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Subscriber
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "Subscriber(id=$id, username='$username', password='$password')"
}