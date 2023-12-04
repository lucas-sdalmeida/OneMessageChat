package com.lucassdalmeida.onemessagechat.persistence.firebase.subscriber

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
    private val subscribers = mutableMapOf<UUID, Subscriber>()

    init {
        databaseReference.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<SubscriberDTO>()?.let {
                    val subscriber = Subscriber(UUID.fromString(it.id), it.username, it.password)
                    subscribers[subscriber.id] = subscriber
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue<SubscriberDTO>()?.let {
                    val subscriber = Subscriber(UUID.fromString(it.id), it.username, it.password)
                    subscribers[subscriber.id] = subscriber
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.getValue<SubscriberDTO>()?.let {
                    subscribers.remove(UUID.fromString(it.id))
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })

        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.getValue<Map<String, SubscriberDTO>>()?.let { dtos ->
                    Log.d("SubscriberRepository", dtos.toString())
                    dtos.values
                        .map { Subscriber(UUID.fromString(it.id), it.username, it.password) }
                        .forEach { subscribers[it.id] = it  }
                    Log.d("SubscriberRepository", subscribers.values.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not implemented")
            }
        })
    }

    override fun create(subscriber: Subscriber) {
        databaseReference.child(subscriber.id.toString()).setValue(SubscriberDTO().also {
            it.id = subscriber.id.toString()
            it.username = subscriber.username
            it.password = subscriber.password
        })
    }

    override fun findById(id: UUID) = subscribers[id]

    override fun findByUsername(username: String): Subscriber? {
        Log.d("SubscriberRepository", subscribers.values.toString())
        return subscribers.values
            .find { it.username == username }
    }

    override fun contains(id: UUID) = id in subscribers

    override fun contains(username: String) = subscribers.values.any { it.username == username }

    private class SubscriberDTO {
        lateinit var id: String
        lateinit var username: String
        var password: Int = 0
    }
}