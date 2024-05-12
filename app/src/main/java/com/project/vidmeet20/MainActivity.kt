package com.project.vidmeet20

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.project.vidmeet20.databinding.ActivityMainBinding
import io.antmedia.webrtcandroidframework.api.IWebRTCClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webRtcClient: IWebRTCClient

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
        webRtcClient = IWebRTCClient.builder()
            .setActivity(this)
            .setLocalVideoRenderer(binding.surfaceRender)
            .setServerUrl("wss://test.antmedia.io:5443/WebRTCAppEE/websocket")
            .build()

        setClick()
    }

    private fun setClick() {
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
    }

}