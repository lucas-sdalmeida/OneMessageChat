package com.lucassdalmeida.onemessagechat.persistence.firebase.chat

import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.lucassdalmeida.onemessagechat.domain.application.chat.repository.ChatRepository
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import java.util.UUID

class RealtimeChatRepository: ChatRepository {
    private val chats = mutableMapOf<String, Chat>()

    private val databaseReference = Firebase.database.getReference(CHAT_ROOT_NODE)

    init {
        addChildEventListener()
        fetchChildrenOnce()
    }

    companion object {
        const val CHAT_ROOT_NODE = "chat-repository"
    }

    private fun addChildEventListener() {
        databaseReference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<ChatDTO>()?.let {
                    chats[it.id] = Chat(
                        it.id,
                        it.message,
                        it.subscribers.map { s -> UUID.fromString(s) }.toSet()
                    )
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<ChatDTO>()?.let {
                    chats[it.id] = Chat(
                        it.id,
                        it.message,
                        it.subscribers.map { s -> UUID.fromString(s) }.toSet()
                    )
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.getValue<Chat>()?.let {
                    chats.remove(it.id)
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })
    }

    private fun fetchChildrenOnce() {
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<Map<String, ChatDTO>>()?.let {
                    it.values.map { dto ->
                        Chat(
                            dto.id,
                            dto.message,
                            dto.subscribers.map { s -> UUID.fromString(s) }.toSet(),
                        )
                    }.forEach { dto -> chats[dto.id] = dto }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })
    }

    override fun create(chat: Chat) {
        databaseReference.child(chat.id).setValue(ChatDTO().also {
            it.id = chat.id
            it.message = chat.message
            it.subscribers = chat.subscribers.map { s -> s.toString() }.toList()
        })
    }

    override fun findById(id: String) = chats[id]

    override fun findAllBySubscriberId(subscriberId: UUID) = chats.values
        .filter { subscriberId in it.subscribers }
        .toList()

    override fun delete(id: String) {
        databaseReference.child(id).removeValue()
    }

    override fun contains(id: String) = id in chats

    private class ChatDTO() {
        lateinit var id: String
        lateinit var message: String
        lateinit var subscribers: List<String>
    }
}