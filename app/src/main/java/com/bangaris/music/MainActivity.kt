package com.bangaris.music

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.graphics.Color
import android.view.Gravity
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : Activity() {

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView
    private lateinit var status: TextView

    private val chooseMedia =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                try {
                    contentResolver.takePersistableUriPermission(
                        it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: Exception) {
                }

                player?.setMediaItem(MediaItem.fromUri(it))
                player?.prepare()
                player?.play()
                status.text = "⚡ Sedang memutar media"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(5, 15, 40)
        window.navigationBarColor = Color.rgb(5, 15, 40)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(20, 24, 20, 20)
            setBackgroundColor(Color.rgb(5, 15, 40))
        }

        val title = TextView(this).apply {
            text = "⚡ ARIS MUSIK PLAYER"
            textSize = 25f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setPadding(0, 12, 0, 8)
        }

        val subtitle = TextView(this).apply {
            text = "MUSIC • VIDEO • YOUR LIBRARY"
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(Color.rgb(80, 180, 255))
        }

        status = TextView(this).apply {
            text = "Pilih musik atau video dari HP"
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            setPadding(8, 24, 8, 16)
        }

        playerView = PlayerView(this).apply {
            useController = true
        }

        val pickButton = Button(this).apply {
            text = "＋ PILIH MUSIK / VIDEO"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.rgb(20, 105, 230))
            setOnClickListener {
                chooseMedia.launch(arrayOf("audio/*", "video/*"))
            }
        }

        val footer = TextView(this).apply {
            text = "BANG ARIS • MUSIC EXPERIENCE"
            textSize = 11f
            gravity = Gravity.CENTER
            setTextColor(Color.LTGRAY)
            setPadding(8, 18, 8, 8)
        }

        root.addView(title, LinearLayout.LayoutParams(-1, -2))
        root.addView(subtitle, LinearLayout.LayoutParams(-1, -2))
        root.addView(status, LinearLayout.LayoutParams(-1, -2))
        root.addView(
            playerView,
            LinearLayout.LayoutParams(-1, 0, 1f)
        )
        root.addView(pickButton, LinearLayout.LayoutParams(-1, -2))
        root.addView(footer, LinearLayout.LayoutParams(-1, -2))

        setContentView(root)
        player = ExoPlayer.Builder(this).build()
        playerView.player = player
    }

    override fun onDestroy() {
        player?.release()
        player = null
        super.onDestroy()
    }
}
