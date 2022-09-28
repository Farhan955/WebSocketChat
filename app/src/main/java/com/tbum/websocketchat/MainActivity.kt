package com.tbum.websocketchat

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.tbum.websocketchat.databinding.ActivityMainBinding
import okhttp3.*
import okio.ByteString

class MainActivity : AppCompatActivity() {

    val context = this
    lateinit var binding: ActivityMainBinding
    lateinit var socket: WebSocket

    lateinit var adapter: MessageAdapter
    var chatList = ArrayList<Message>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _init_()
        initSocket()


    }

    private fun _init_() {
        adapter = MessageAdapter(context, chatList)
        binding.recyclerView.adapter = adapter
    }

    private fun initSocket() {

        var client = OkHttpClient()

        val request: Request = Request.Builder().url("ws://192.168.10.10:8080").build()
        var listener = SocketListener(this)
        socket = client.newWebSocket(request, listener)
    }

    fun btnSend(view: View) {

        val msg = binding.etMsg.text.toString()
        binding.etMsg.setText("")
        val obj = Message(msg, false)
        socket.send(Gson().toJson(obj))
        adapter.addItem(obj)


    }


    class SocketListener(var activity: MainActivity) : WebSocketListener() {


        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)


            activity.runOnUiThread {
                Toast.makeText(activity, "Connection is established", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)

            activity.runOnUiThread {
                val obj = Message(text, true)
                activity.adapter.addItem(obj)

            }

        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
        }


    }

}