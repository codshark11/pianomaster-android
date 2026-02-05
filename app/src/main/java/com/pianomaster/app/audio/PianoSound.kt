package com.pianomaster.app.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sin

/**
 * Plays a short sine wave tone for a given MIDI note.
 * Formula: frequency = 440 * 2^((midi - 69) / 12)
 */
object PianoSound {
    private const val SAMPLE_RATE = 44100
    private const val DURATION_MS = 200
    private const val AMPLITUDE = 0.3
    private val bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)

    fun midiToFrequency(midi: Int): Double = 440.0 * 2.0.pow((midi - 69) / 12.0)

    fun playNote(midi: Int) {
        Thread {
            try {
                val frequency = midiToFrequency(midi)
                val numSamples = (SAMPLE_RATE * DURATION_MS / 1000.0).toInt()
                val buffer = ShortArray(numSamples)
                val twoPiF = 2.0 * PI * frequency / SAMPLE_RATE
                for (i in 0 until numSamples) {
                    buffer[i] = (Short.MAX_VALUE * AMPLITUDE * sin(twoPiF * i)).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
                }
                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_GAME)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(SAMPLE_RATE)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(bufferSize)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()
                audioTrack.play()
                audioTrack.write(buffer, 0, buffer.size)
                audioTrack.stop()
                audioTrack.release()
            } catch (_: Exception) { }
        }.start()
    }
}
