package com.lucassdalmeida.onemessagechat.controller

import com.lucassdalmeida.onemessagechat.databinding.ActivityMainBinding
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat
import com.lucassdalmeida.onemessagechat.view.MainActivity
import com.lucassdalmeida.onemessagechat.view.adapter.ChatListViewAdapter

class MainActivityController(
    private val mainActivity: MainActivity,
    private val activityMainBinding: ActivityMainBinding,
) {
    private val chatList = mutableListOf<Chat>()
    private val chatListViewAdapter = ChatListViewAdapter(mainActivity, chatList)
}