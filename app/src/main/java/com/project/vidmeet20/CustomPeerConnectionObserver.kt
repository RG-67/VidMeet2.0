package com.project.vidmeet20

import org.json.JSONObject
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.RtpReceiver
import java.net.URI

open class CustomPeerConnectionObserver(s: String, peerConnection: PeerConnection) :
    PeerConnection.Observer {

    private var tag: String
    private var prConnection: PeerConnection
    private val webSocketVideoClient = WebSocketVideoClient(URI("ws://localhost:8080"))

    init {
        this.tag = s
        this.prConnection = peerConnection
    }

    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
        TODO("Not yet implemented")
    }

    override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
        TODO("Not yet implemented")
    }

    override fun onIceConnectionReceivingChange(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
        TODO("Not yet implemented")
    }

    override fun onIceCandidate(p0: IceCandidate?) {
        if (p0 != null) {
            sendIceCandidateToRemote(p0)
        }
    }

    override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
        TODO("Not yet implemented")
    }

    override fun onAddStream(p0: MediaStream?) {
        TODO("Not yet implemented")
    }

    override fun onRemoveStream(p0: MediaStream?) {
        TODO("Not yet implemented")
    }

    override fun onDataChannel(p0: DataChannel?) {
        TODO("Not yet implemented")
    }

    override fun onRenegotiationNeeded() {
        TODO("Not yet implemented")
    }

    override fun onAddTrack(p0: RtpReceiver?, p1: Array<out MediaStream>?) {
        TODO("Not yet implemented")
    }

    private fun sendIceCandidateToRemote(iceCandidate: IceCandidate) {
        try {
            val candidateJson = JSONObject()
            candidateJson.put("sdpMid", iceCandidate.sdpMid)
            candidateJson.put("sdpMLineIndex", iceCandidate.sdpMLineIndex)
            candidateJson.put("candidate", iceCandidate.sdp)
            webSocketVideoClient.send(candidateJson.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun receiveIceCandidate(candidateJson: JSONObject) {
        try {
            val candidate = IceCandidate(
                candidateJson.getString("sdpMid"),
                candidateJson.getInt("sdpMLineIndex"),
                candidateJson.getString("candidate")
            )
            prConnection.addIceCandidate(candidate)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}