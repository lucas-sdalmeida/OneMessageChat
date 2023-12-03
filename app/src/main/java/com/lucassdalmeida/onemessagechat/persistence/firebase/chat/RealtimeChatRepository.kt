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
                snapshot.getValue<Chat>()?.let {
                    chats[it.id] = it
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<Chat>()?.let {
                    chats[it.id] = it
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
                snapshot.getValue<Map<String, Chat>>()?.let {
                    chats.putAll(it)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })
    }

    override fun create(chat: Chat) {
        databaseReference.child(chat.id).setValue(chat)
    }

    override fun findById(id: String) = chats[id]

    override fun findAll() = chats.values.toList()

    override fun delete(id: String) {
        databaseReference.child(id).removeValue()
    }

    override fun contains(id: String) = id in chats
}