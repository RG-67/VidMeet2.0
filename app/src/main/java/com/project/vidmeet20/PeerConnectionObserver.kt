package com.project.vidmeet20

import android.util.Log
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.RtpReceiver

class PeerConnectionObserver : PeerConnection.Observer {
    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
        Log.d("PeerConnectionOnSigChange-->", "onSignalState: $p0");
    }

    override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
        Log.d("PeerIceConnectionChange-->", "onIceConnectionChange: $p0");
    }

    override fun onIceConnectionReceivingChange(p0: Boolean) {
        Log.d("PeerOnIceConReChange-->", "onIceConReChange: $p0");
    }

    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
        Log.d("PeerConnectionOnIceGathering-->", "onIceGatheringState: $p0");
    }

    override fun onIceCandidate(p0: IceCandidate?) {
        Log.d("PeerConnectionOnIceCandidate-->", "onIceCandidate: $p0");
    }

    override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
        Log.d("PeerConnectionOnIceCanRemove-->", "onIceCanRemove: $p0");
    }

    override fun onAddStream(p0: MediaStream?) {
        Log.d("PeerConnectionOnAddStream-->", "onAddStream: $p0");
    }

    override fun onRemoveStream(p0: MediaStream?) {
        Log.d("PeerConnectionOnRemoveStream-->", "onRemoveStream: $p0");
    }

    override fun onDataChannel(p0: DataChannel?) {
        Log.d("PeerConnectionOnDataChanel-->", "onDataChannel: $p0");
    }

    override fun onRenegotiationNeeded() {

    }

    override fun onAddTrack(p0: RtpReceiver?, p1: Array<out MediaStream>?) {
        Log.d("PeerConnectionOnAddTrack-->", "onAddTrack: $p0");
    }



}