package com.lucassdalmeida.onemessagechat.domain.application.chat.repository

import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat

interface ChatRepository {
    fun create(chat: Chat)

    fun findById(id: String): Chat?

    fun findAll(): List<Chat>

    fun delete(id: String)

    operator fun contains(id: String): Boolean
}