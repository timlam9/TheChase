package com.lamti.thechase

import android.content.Context
import android.content.res.Resources
import android.media.MediaPlayer
import android.util.Log

data class AudioPlayer(
    private val context: Context,
    private val resources: Resources
) {

    private var backgroundPlayer: MediaPlayer? = null
    private var player: MediaPlayer? = null

    fun playBackgroundMusic(music: Int) {
        if (backgroundPlayer == null) backgroundPlayer = MediaPlayer.create(context, music)

        backgroundPlayer?.run {
            try {
                reset()
                setDataSource(resources.openRawResourceFd(music))
//                setAudioAttributes(AudioAttributes.CONTENT_TYPE_MUSIC)
                prepare()
                start()
            } catch (e: Exception) {
                Log.d("AUDIO_PLAYER_LOG", "Set datasource error: ${e.message}")
            }
        }
    }

    fun pauseMusic() {
        if (backgroundPlayer?.isPlaying == true) backgroundPlayer?.pause()
    }

    fun stopMusic() {
        backgroundPlayer = backgroundPlayer?.run {
            stop()
            release()
            null
        }
    }

    fun playSound(sound: Int) {
        if (player == null) player = MediaPlayer.create(context, sound)

        player?.run {
            try {
                reset()
                setDataSource(resources.openRawResourceFd(sound))
//                setAudioAttributes(AudioAttributes.CONTENT_TYPE_MUSIC)
                prepare()
                start()
            } catch (e: Exception) {
                Log.d("AUDIO_PLAYER_LOG", "Set datasource error: ${e.message}")
            }
        }
    }

    fun pauseSound() {
        if (player?.isPlaying == true) player?.pause()
    }

    fun stopSound() {
        player = player?.run {
            stop()
            release()
            null
        }
    }
}