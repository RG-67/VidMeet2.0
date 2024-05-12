package com.project.vidmeet20

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.vidmeet20.databinding.ActivityMainBinding
import io.antmedia.webrtcandroidframework.api.IWebRTCClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        val webRtcClient: IWebRTCClient = IWebRTCClient.builder()
            .setActivity(this)
            .setLocalVideoRenderer(binding.surfaceRender)
            .setServerUrl("wss://test.antmedia.io:5443/WebRTCAppEE/websocket")
            .build()
//        webRtcClient.publish("StreamRTK")
        webRtcClient.play("stream1")


    }
}