package com.project.vidmeet20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.project.vidmeet20.databinding.ActivityMainBinding
import org.webrtc.AudioSource
import org.webrtc.AudioTrack
import org.webrtc.Camera1Enumerator
import org.webrtc.CameraEnumerator
import org.webrtc.EglBase
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer
import org.webrtc.PeerConnection.RTCConfiguration
import org.webrtc.PeerConnectionFactory
import org.webrtc.SessionDescription
import org.webrtc.SurfaceTextureHelper
import org.webrtc.VideoCapturer
import org.webrtc.VideoSource
import org.webrtc.VideoTrack


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //    private lateinit var webRtcClient: IWebRTCClient
    private var randomID = ""
    private lateinit var peerConnectionFactory: PeerConnectionFactory

    companion object {
        const val sub = "Your Meeting ID - "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        randomID = "VID" + (10 + Math.random() * 10).toInt().toString()
        binding.edtMeetID.setText(randomID)
        binding.shareBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Meeting ID - ")
            intent.putExtra(Intent.EXTRA_TEXT, sub + randomID)
            startActivity(Intent.createChooser(intent, "Share via"))
        }
        Log.d("RandomID-->", randomID)
        setInit()
//        setClick()
    }

    private fun setInit() {
        val builder: PeerConnectionFactory.InitializationOptions.Builder =
            PeerConnectionFactory.InitializationOptions.builder(this)
        val initOptions: PeerConnectionFactory.InitializationOptions =
            builder.createInitializationOptions()
        PeerConnectionFactory.initialize(initOptions)
        peerConnectionFactory = PeerConnectionFactory.builder().createPeerConnectionFactory()

        val videoCapturer: VideoCapturer? = createCameraCapturer(Camera1Enumerator(false))
        val videoSource: VideoSource? = videoCapturer?.isScreencast
            ?.let { peerConnectionFactory.createVideoSource(it) }

        val eglBase: EglBase.Context = EglBase.create().eglBaseContext
        val surfaceTextureHelper: SurfaceTextureHelper =
            SurfaceTextureHelper.create("CaptureThread", eglBase)
        videoCapturer?.initialize(surfaceTextureHelper, this, videoSource?.capturerObserver)
        videoCapturer?.startCapture(250, 250, 30) /*Video_HEIGHT,VIDEO_WIDTH, VIDEO_FPS*/

        val videoTrack: VideoTrack =
            peerConnectionFactory.createVideoTrack("VIDEO_TRACK_ID", videoSource)
        val audioSource: AudioSource = peerConnectionFactory.createAudioSource(MediaConstraints())
        val audioTrack: AudioTrack =
            peerConnectionFactory.createAudioTrack("AUDIO_TRACK_ID", audioSource)

        val iceServers: ArrayList<IceServer> = ArrayList()
        iceServers.add(IceServer.builder("stun:stun.l.google.com:19302").createIceServer())
        iceServers.add(
//            IceServer.builder("turn:your-turn-server.com:3478")
            IceServer.builder("turn:stun.l.google.com:19302")
                .setUsername("")
                .setPassword("")
                .createIceServer()
        )

        val rtcConfiguration: PeerConnection.RTCConfiguration =
            PeerConnection.RTCConfiguration(iceServers)
        val peerConnection: PeerConnection? = peerConnectionFactory.createPeerConnection(rtcConfiguration, object: CustomPeerConnectionObserver("localPeerCreation") {
            override fun onIceCandidate(p0: IceCandidate?) {
                super.onIceCandidate(p0)
            }

            override fun onAddStream(p0: MediaStream?) {
                super.onAddStream(p0)
            }
        })

        peerConnection?.createOffer(object: CustomSdpObserver("localCreateOffer") {
            override fun onCreateSuccess(p0: SessionDescription?) {
                super.onCreateSuccess(p0)
                peerConnection.setLocalDescription(CustomSdpObserver("localSetLocalDesc"), p0)
            }
        }, MediaConstraints())

//        peerConnection?.setRemoteDescription(object: CustomSdpObserver(""), )

    }

    private fun createCameraCapturer(enumerator: CameraEnumerator): VideoCapturer? {
        val deviceNames = enumerator.deviceNames
//        Looking for front facing camera
        for (deviceName in deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                val videoCapturer: VideoCapturer = enumerator.createCapturer(deviceName, null)
                if (videoCapturer != null) {
                    return videoCapturer
                }
            }
        }
//        If front facing camera not found looking for another camera
        for (deviceName in deviceNames) {
            if (!enumerator.isFrontFacing(deviceName)) {
                val videoCapturer: VideoCapturer = enumerator.createCapturer(deviceName, null)
                if (videoCapturer != null) {
                    return videoCapturer
                }
            }
        }
        return null
    }

    /*private fun setClick() {
        binding.startBtn.setOnClickListener {
            if (binding.edtID.text.toString() == "") {
                binding.edtID.error = "Enter ID"
                Snackbar.make(it, "Enter ID", Snackbar.LENGTH_SHORT).show()
            } else {
                binding.stAndPlLin.visibility = View.GONE
                binding.surfaceRender.visibility = View.VISIBLE
                webRtcClient = IWebRTCClient.builder()
                    .setActivity(this)
                    .setLocalVideoRenderer(binding.surfaceRender)
                    .setServerUrl("wss://test.antmedia.io:5443/WebRTCAppEE/websocket")
                    .build()
                webRtcClient.publish(binding.edtID.text.toString())
            }
        }
        binding.playBtn.setOnClickListener {
            if (binding.edtID.text.toString() == "") {
                binding.edtID.error = "Enter ID"
                Snackbar.make(it, "Enter ID", Snackbar.LENGTH_SHORT).show()
            } else {
                binding.stAndPlLin.visibility = View.GONE
                binding.surfaceRender.visibility = View.VISIBLE
                webRtcClient = IWebRTCClient.builder()
                    .setActivity(this)
                    .addRemoteVideoRenderer(binding.surfaceRender)
                    .setServerUrl("wss://test.antmedia.io:5443/WebRTCAppEE/websocket")
                    .build()
                webRtcClient.play(binding.edtID.text.toString())
            }
        }
    }*/

}