package com.lucassdalmeida.onemessagechat.domain.entities.user

import java.util.UUID

class User(
    val id: UUID,
    val username: String,
) {
    init {
        require(username.isNotBlank()) { "The username cannot be an blank string!" }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString() = "User(id=$id, username='$username')"
}