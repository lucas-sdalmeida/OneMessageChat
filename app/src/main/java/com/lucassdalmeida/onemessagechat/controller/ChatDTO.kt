package com.lucassdalmeida.onemessagechat.controller

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
class ChatDTO(
    val id: String,
    val message: String,
    val subscribers: List<UUID>,
): Parcelable
