package com.lucassdalmeida.onemessagechat.controller

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.databinding.ActivityMainBinding
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import com.lucassdalmeida.onemessagechat.persistence.firebase.chat.RealtimeChatRepository
import com.lucassdalmeida.onemessagechat.view.ChatActivity
import com.lucassdalmeida.onemessagechat.view.LoginOrSignupDialog
import com.lucassdalmeida.onemessagechat.view.MainActivity
import com.lucassdalmeida.onemessagechat.view.adapter.ChatListViewAdapter
import java.util.UUID

class MainActivityController(
    private val mainActivity: MainActivity,
    private val activityMainBinding: ActivityMainBinding,
    subscriber: UUID?,
) {
    private val chatRepository = RealtimeChatRepository()
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
            chatRepository.findAllBySubscriberId(subscriberId).forEach {
                chatList.add(it)
            }
            chatListViewAdapter.notifyDataSetChanged()
        }
    }

    fun onSaveInstanceState(bundle: Bundle) {
        bundle.putSerializable("SUBSCRIBER_ID", subscriber)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addChatMainMenuOption -> addChat()
            else -> false
        }
    }

    private fun addChat(): Boolean {
        Intent(mainActivity, ChatActivity::class.java).also {
            it.putExtra("SUBSCRIBER_ID", subscriber)
            mainActivity.startActivity(it)
        }
        return true
    }
}