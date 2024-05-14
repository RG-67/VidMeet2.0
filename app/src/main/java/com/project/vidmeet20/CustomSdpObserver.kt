package com.project.vidmeet20

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

open class CustomSdpObserver(s: String): SdpObserver {

    private var tag: String

    init {
        this.tag = s
    }

    override fun onCreateSuccess(p0: SessionDescription?) {
        TODO("Not yet implemented")
    }

    override fun onSetSuccess() {
        TODO("Not yet implemented")
    }

    override fun onCreateFailure(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onSetFailure(p0: String?) {
        TODO("Not yet implemented")
    }
}