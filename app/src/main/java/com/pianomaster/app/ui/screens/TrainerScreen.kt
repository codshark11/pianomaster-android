package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.audio.PianoSound
import com.pianomaster.app.data.SongsRepository
import com.pianomaster.app.ui.components.FloatingBackButton
import com.pianomaster.app.ui.components.NoteCascade
import com.pianomaster.app.ui.components.PianoKeys
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun TrainerScreen(
    songId: String,
    onBack: () -> Unit,
    onSettings: () -> Unit = {}
) {
    val song = remember(songId) { SongsRepository.getSongById(songId) }
    var visualModeCascade by remember { mutableStateOf(true) }
    var activeNotes by remember { mutableStateOf(setOf<Int>()) }
    val progress = remember { mutableStateOf(0f) }
    val accuracy = 98

    if (song == null) {
        Box(Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
            Text("Song not found", color = TextSecondary)
        }
        return
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Background)
        ) {
            if (visualModeCascade && song.notes.isNotEmpty()) {
                NoteCascade(
                    song = song,
                    currentTime = progress.value,
                    activeNotes = activeNotes.toList()
                )
            } else if (!visualModeCascade) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Sheet music view",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No notes in this song",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingBackButton(onClick = onBack)
            IconButton(onClick = onSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = TextPrimary)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(Color(0xFF0C0C0C))
        ) {
            PianoKeys(
                activeNotes = activeNotes.toList(),
                onNoteDown = { midi ->
                    activeNotes = activeNotes + midi
                    PianoSound.playNote(midi)
                },
                onNoteUp = { midi -> activeNotes = activeNotes - midi },
                startMidi = 48,
                endMidi = 72
            )
        }
    }
}
