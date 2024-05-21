package com.project.vidmeet20

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONException
import org.json.JSONObject
import java.net.URI


class WebSocketVideoClient(serverUri: URI) : WebSocketClient(serverUri) {

    private val client = WebSocketVideoClient(URI("ws://localhost:8080"))

    override fun onOpen(handshakedata: ServerHandshake?) {
        TODO("Not yet implemented")
    }

    override fun onMessage(message: String?) {
        message?.let {
            try {
                val sdpJson = JSONObject(it)
                val type = sdpJson.getString("type")
                val sdp = sdpJson.getString("sdp")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun connect() {
        super.connect()
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }

    /*companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = WebSocketVideoClient(URI("ws://localhost:8080"))
            client.connect()
            setSdp(client)
        }
        private fun setSdp(client: WebSocketClient) {
//            sending sdp message
            val sdpJson = JSONObject()
            sdpJson.put("type", "offer")
            sdpJson.put("sdp", "") *//*insert sdp string*//*
            client.send(sdpJson.toString())

        }

    }*/

    fun createClient(offerSdp: String) {
        client.connect()
        val sdpJson = JSONObject()
        sdpJson.put("type", "offer")
        sdpJson.put("sdp", offerSdp)
        client.send(sdpJson.toString())
    }

    override fun send(message: String) {
        client.send(message)
    }

}