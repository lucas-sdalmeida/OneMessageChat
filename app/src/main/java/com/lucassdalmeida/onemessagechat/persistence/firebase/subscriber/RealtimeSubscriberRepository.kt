package com.lucassdalmeida.onemessagechat.persistence.firebase.subscriber

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.lucassdalmeida.onemessagechat.domain.application.subcriber.repository.SubscriberRepository
import com.lucassdalmeida.onemessagechat.domain.entities.subscriber.Subscriber
import java.util.UUID

class RealtimeSubscriberRepository: SubscriberRepository {
    companion object {
        const val SUBSCRIBER_ROOT_NODE = "subscriber-repository"
    }

    private var databaseReference = Firebase.database.getReference(SUBSCRIBER_ROOT_NODE)

    override fun create(subscriber: Subscriber) {
        databaseReference.child(subscriber.id.toString()).setValue(subscriber)
    }

    override fun findById(id: UUID): Subscriber? {
        var user: Subscriber? = null
        databaseReference.child(id.toString())
            .addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    user = snapshot.value as Subscriber?
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not implemented")
                }
            })
        return user
    }

    override fun findByUsername(username: String): Subscriber? {
        return findAll().find { it.username == username }
    }

    private fun findAll(): List<Subscriber> {
        val subscribers = mutableListOf<Subscriber>()
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<Map<String, Subscriber>>()?.let { children ->
                    children.values.forEach { subscribers.add(it) }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })

        return subscribers
    }

    override fun contains(id: UUID): Boolean {
        return findById(id) != null
    }

    override fun contains(username: String): Boolean {
        return findByUsername(username) != null
    }
}