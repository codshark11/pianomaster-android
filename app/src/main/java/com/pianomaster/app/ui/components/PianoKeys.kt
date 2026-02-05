package com.pianomaster.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.pianomaster.app.ui.theme.BlackKey
import com.pianomaster.app.ui.theme.BlackKeyActive
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextSecondary
import com.pianomaster.app.ui.theme.WhiteKey
import com.pianomaster.app.ui.theme.WhiteKeyActive
import com.pianomaster.app.utils.getNoteFromMidi
import com.pianomaster.app.utils.isBlackKey

@Composable
fun PianoKeys(
    activeNotes: List<Int>,
    onNoteDown: (Int) -> Unit,
    onNoteUp: (Int) -> Unit,
    startMidi: Int = 48,
    endMidi: Int = 72,
    modifier: Modifier = Modifier
) {
    val keys = (startMidi..endMidi).toList()
    val whiteKeys = keys.filter { !isBlackKey(it) }
    val whiteCount = whiteKeys.size

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val whiteKeyWidthDp = maxWidth / whiteCount
        val blackKeyWidthDp = whiteKeyWidthDp * 0.7f
        val blackKeyHeightDp = maxHeight * 0.65f

        Row(modifier = Modifier.fillMaxSize()) {
            keys.filter { !isBlackKey(it) }.forEach { midi ->
                val isActive = activeNotes.contains(midi)
                val noteInfo = getNoteFromMidi(midi)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            if (isActive) WhiteKeyActive else WhiteKey,
                            RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
                        )
                        .border(1.dp, Color(0xFF0F172A))
                        .pointerInput(midi) {
                            detectTapGestures(
                                onPress = {
                                    onNoteDown(midi)
                                    tryAwaitRelease()
                                    onNoteUp(midi)
                                }
                            )
                        },
                    contentAlignment = Alignment.BottomCenter
                ) {
                    if (noteInfo.note == "C") {
                        Text(
                            "C${noteInfo.octave}",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }
        var whiteIdx = 0
        keys.forEach { midi ->
            if (isBlackKey(midi)) {
                val leftOffsetDp = (whiteIdx - 0.35f) * whiteKeyWidthDp.value
                val isActive = activeNotes.contains(midi)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(leftOffsetDp.dp, 0.dp)
                        .size(blackKeyWidthDp, blackKeyHeightDp)
                        .background(
                            if (isActive) BlackKeyActive else BlackKey,
                            RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp)
                        )
                        .pointerInput(midi) {
                            detectTapGestures(
                                onPress = {
                                    onNoteDown(midi)
                                    tryAwaitRelease()
                                    onNoteUp(midi)
                                }
                            )
                        }
                )
            } else {
                whiteIdx++
            }
        }
    }
}
