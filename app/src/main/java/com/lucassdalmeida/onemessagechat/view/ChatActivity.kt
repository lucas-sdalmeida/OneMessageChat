package com.lucassdalmeida.onemessagechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.controller.ChatActivityController
import com.lucassdalmeida.onemessagechat.databinding.ActivityChatBinding
import java.util.UUID

class ChatActivity : AppCompatActivity() {
    private val activityChatBinding by lazy {
        ActivityChatBinding.inflate(layoutInflater)
    }
    private lateinit var chatActivityController: ChatActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityChatBinding.root)
        setSupportActionBar(activityChatBinding.chatActivityToolbar.root)

        val subscriberId = intent.getSerializableExtra("SUBSCRIBER_ID") as? UUID ?:
            throw IllegalStateException("No subscriber has been passed!")
        chatActivityController = ChatActivityController(
            subscriberId,
            this,
            activityChatBinding
        )
    }
}