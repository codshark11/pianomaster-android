package com.pianomaster.app.utils

private val NOTES = listOf("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B")

data class NoteInfo(val note: String, val octave: Int, val midi: Int, val name: String)

fun getNoteFromMidi(midi: Int): NoteInfo {
    val octave = (midi / 12) - 1
    val noteIndex = midi % 12
    val noteName = NOTES[noteIndex]
    return NoteInfo(noteName, octave, midi, "${noteName}$octave")
}

fun isBlackKey(midi: Int): Boolean = getNoteFromMidi(midi).note.contains("#")
