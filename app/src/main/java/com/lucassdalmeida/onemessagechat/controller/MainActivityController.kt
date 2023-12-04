package com.lucassdalmeida.onemessagechat.controller

import android.os.Bundle
import android.widget.Toast
import com.lucassdalmeida.onemessagechat.databinding.ActivityMainBinding
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import com.lucassdalmeida.onemessagechat.view.LoginOrSignupDialog
import com.lucassdalmeida.onemessagechat.view.MainActivity
import com.lucassdalmeida.onemessagechat.view.adapter.ChatListViewAdapter
import java.util.UUID

class MainActivityController(
    private val mainActivity: MainActivity,
    private val activityMainBinding: ActivityMainBinding,
    subscriber: UUID?,
) {
    private val chatList = mutableListOf<Chat>()
    private val chatListViewAdapter = ChatListViewAdapter(mainActivity, chatList)
    private lateinit var subscriber: UUID

    init {
        initializeSubscriber(subscriber)
    }

    private fun initializeSubscriber(id: UUID?) {
        if (id != null) subscriber = id
        LoginOrSignupDialog(mainActivity) {
            subscriber = this@LoginOrSignupDialog.subscriberId
            Toast.makeText(mainActivity, subscriberId.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveInstanceState(bundle: Bundle) {
        bundle.putSerializable("SUBSCRIBER_ID", subscriber)
    }
}