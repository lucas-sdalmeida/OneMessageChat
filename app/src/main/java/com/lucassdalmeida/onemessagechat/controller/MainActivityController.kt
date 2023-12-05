package com.lucassdalmeida.onemessagechat.controller

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.databinding.ActivityMainBinding
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import com.lucassdalmeida.onemessagechat.persistence.firebase.chat.RealtimeChatRepository
import com.lucassdalmeida.onemessagechat.view.ChatActivity
import com.lucassdalmeida.onemessagechat.view.LoginOrSignupDialog
import com.lucassdalmeida.onemessagechat.view.MainActivity
import com.lucassdalmeida.onemessagechat.view.SubscribeDialog
import com.lucassdalmeida.onemessagechat.view.adapter.ChatListViewAdapter
import java.util.UUID

private const val GET_CHATS = 1000

class MainActivityController(
    private val mainActivity: MainActivity,
    private val activityMainBinding: ActivityMainBinding,
    subscriber: UUID?,
) {
    private val chatRepository = RealtimeChatRepository()
    private val chatList = mutableListOf<Chat>()
    private val chatListViewAdapter = ChatListViewAdapter(mainActivity, chatList)
    private val chatActivityResultLauncher = registerForActivityResult()
    private lateinit var subscriber: UUID
    private val handler = object: Handler(mainActivity.mainLooper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == GET_CHATS) {
                updateChats()
                chatListViewAdapter.notifyDataSetChanged()
            }
        }
    }

    init {
        initializeSubscriber(subscriber)
        activityMainBinding.chatListView.adapter = chatListViewAdapter
    }

    private fun initializeSubscriber(id: UUID?) {
        if (id != null) subscriber = id
        LoginOrSignupDialog(mainActivity) {
            subscriber = this@LoginOrSignupDialog.subscriberId
            updateChats()
        }
    }

    fun onSaveInstanceState(bundle: Bundle) {
        bundle.putSerializable("SUBSCRIBER_ID", subscriber)
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addChatMainMenuOption -> addChat()
            R.id.subscribeMainMenuOption -> subscribe()
            else -> false
        }
    }

    private fun addChat(): Boolean {
        Intent(mainActivity, ChatActivity::class.java).also {
            it.putExtra("SUBSCRIBER_ID", subscriber)
            chatActivityResultLauncher.launch(it)
        }
        return true
    }

    private fun subscribe(): Boolean {
        SubscribeDialog(mainActivity, subscriber) {
            handler.sendMessage(handler.obtainMessage().also { it.what = GET_CHATS })
        }
        return true
    }

    private fun registerForActivityResult() = mainActivity.registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        updateChats()
        chatListViewAdapter.notifyDataSetChanged()
    }

    private fun updateChats() {
        Thread {
            chatList.clear()
            chatRepository.findAllBySubscriberId(subscriber)
                .forEach { chatList.add(it) }
            handler.sendMessageDelayed(
                handler.obtainMessage().also { it.what = GET_CHATS },
                3000,
            )
        }.start()
    }
}