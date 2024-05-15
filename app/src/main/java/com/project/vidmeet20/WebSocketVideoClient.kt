package com.project.vidmeet20

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.json.JSONException
import org.json.JSONObject
import java.net.URI


class WebSocketVideoClient(serverUri: URI) : WebSocketClient(serverUri) {

    override fun onOpen(handshakedata: ServerHandshake?) {
        TODO("Not yet implemented")
    }

    override fun onMessage(message: String?) {
        TODO("Not yet implemented")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onError(ex: Exception?) {
        TODO("Not yet implemented")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = WebSocketVideoClient(URI("ws://localhost:8080"))
            client.connect()
//            setSdp(client)
        }
//        private fun setSdp(client: WebSocketClient) {
//            sending sdp message
//            val sdpJson = JSONObject()
//            sdpJson.put("type", "offer")
//            sdpJson.put("sdp", "") /*insert sdp string*/

//            client.send(sdpJson.toString())

//            receiving sdp message
//        }

    }


}