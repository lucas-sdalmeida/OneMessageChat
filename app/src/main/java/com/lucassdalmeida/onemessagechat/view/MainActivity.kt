package com.lucassdalmeida.onemessagechat.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import com.lucassdalmeida.onemessagechat.R
import com.lucassdalmeida.onemessagechat.controller.MainActivityController
import com.lucassdalmeida.onemessagechat.databinding.ActivityMainBinding
import java.util.UUID

private const val GET_CHATS = 100

class MainActivity : AppCompatActivity() {
    private val activityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var controller: MainActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.mainToolbar.root)

        val subscriberId = savedInstanceState?.getSerializable("SUBSCRIBER_ID") as? UUID
        controller = MainActivityController(this, activityMainBinding, subscriberId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_actitvity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return controller.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        controller.onSaveInstanceState(outState)

    }
}