package com.chernysh.smarthome.presentation.camera

import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.chernysh.smarthome.BuildConfig
import com.chernysh.smarthome.databinding.ActivityIpCameraBinding
import org.videolan.libvlc.IVLCVout
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer


/**
 * Created by Andrii Chernysh on 2020-01-12
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class CameraActivity : AppCompatActivity() {
    private lateinit var holder: SurfaceHolder
    private lateinit var libvlc: LibVLC
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var binding: ActivityIpCameraBinding

    private val options = arrayListOf(
        "--aout=opensles",
        "--audio-time-stretch",
        "-vvv",
        "--aout=opensles",
        "--avcodec-codec=h264",
        "--file-logging",
        "--logfile=vlc-log.txt"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityIpCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        holder = binding.videoSurfaceView.holder
        libvlc = LibVLC(applicationContext, options)
        holder.setKeepScreenOn(true)


        mediaPlayer = MediaPlayer(libvlc)

        val vout: IVLCVout = mediaPlayer.vlcVout
        vout.setVideoView(binding.videoSurfaceView)
        vout.attachViews()

        val media = Media(libvlc, Uri.parse(BuildConfig.RTSP_URL))

        mediaPlayer.media = media
        mediaPlayer.play()
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        mediaPlayer.stop()
        val vout: IVLCVout = mediaPlayer.vlcVout
        vout.detachViews()
        libvlc.release()
    }
}