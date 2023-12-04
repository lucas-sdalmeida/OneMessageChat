package com.lucassdalmeida.onemessagechat.view.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.databinding.ChatPrevTileBinding
import com.lucassdalmeida.onemessagechat.domain.entities.chat.Chat

class ChatListViewAdapter(
    context: Context,
    private val chatList: MutableList<Chat>,
): ArrayAdapter<Chat>(context, R.layout.chat_prev_tile, chatList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val chat = chatList[position]
        val target = convertView ?: createTile()
        val holder = target.tag as ChatPrevTileHolder

        with(holder) {
            chatId.text = chat.id
            messagePreview.text = when {
                chat.message.length > 20 -> "${chat.message.substring(0..16)}..."
                else -> chat.message
            }
        }

        return target
    }

    private fun createTile(): View {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val chatPreviewBinding = ChatPrevTileBinding.inflate(inflater)
        val chatPreviewTileHolder = ChatPrevTileHolder(
            chatPreviewBinding.chatId,
            chatPreviewBinding.messagePreview,
        )
        return chatPreviewBinding.root.also {
            it.tag = chatPreviewTileHolder
        }
    }

    private data class ChatPrevTileHolder(
        val chatId: TextView,
        val messagePreview: TextView,
    )
}