package com.pianomaster.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.pianomaster.app.data.Song
import com.pianomaster.app.ui.theme.CascadeCyan
import com.pianomaster.app.ui.theme.CascadeEmerald
import com.pianomaster.app.utils.getNoteFromMidi
import com.pianomaster.app.utils.isBlackKey
import kotlin.math.roundToInt

private const val WINDOW_SECONDS = 3f
private const val HIT_LINE_FRACTION = 0.9f

private data class NotePosition(val left: Float, val width: Float, val isBlack: Boolean)

@Composable
fun NoteCascade(
    song: Song,
    currentTime: Float,
    activeNotes: List<Int>,
    startMidi: Int = 48,
    endMidi: Int = 72,
    modifier: Modifier = Modifier
) {
    val keys = (startMidi..endMidi).toList()
    val whiteKeys = keys.filter { !isBlackKey(it) }
    val whiteKeyWidthFraction = 1f / whiteKeys.size
    val notePositions = buildNotePositions(keys, whiteKeyWidthFraction)
    val visibleNotes = song.notes.filter { n ->
        n.time < currentTime + WINDOW_SECONDS && n.time + n.duration > currentTime - 0.5
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF09090B))
    ) {
        val widthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val heightPx = with(LocalDensity.current) { maxHeight.toPx() }
        val density = LocalDensity.current

        visibleNotes.forEach { note ->
            val pos = notePositions[note.midi] ?: return@forEach
            val timeToHit = (note.time - currentTime).toFloat()
            val topFraction = HIT_LINE_FRACTION - (timeToHit / WINDOW_SECONDS * HIT_LINE_FRACTION)
            val heightFraction = (note.duration / WINDOW_SECONDS * HIT_LINE_FRACTION).toFloat().coerceIn(0.01f, 1f)
            val isLeftHand = note.midi < 60
            val color = if (isLeftHand) CascadeCyan else CascadeEmerald
            val leftPx = (pos.left * widthPx).roundToInt()
            val widthPxNote = (pos.width * widthPx).roundToInt().coerceAtLeast(4)
            val topPx = (topFraction * heightPx - heightFraction * heightPx).roundToInt().coerceAtLeast(0)
            val noteHeightPx = (heightFraction * heightPx).roundToInt().coerceAtLeast(8)

            Box(
                modifier = Modifier
                    .offset { IntOffset(leftPx, topPx) }
                    .size(with(density) { widthPxNote.toDp() }, with(density) { noteHeightPx.toDp() })
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(color)
                    .then(
                        if (activeNotes.contains(note.midi)) Modifier.background(Color.White.copy(alpha = 0.2f))
                        else Modifier
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (heightFraction > 0.05f) {
                    Text(
                        getNoteFromMidi(note.midi).name,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White
                    )
                }
            }
        }
    }
}

private fun buildNotePositions(keys: List<Int>, whiteKeyWidthFraction: Float): Map<Int, NotePosition> {
    val result = mutableMapOf<Int, NotePosition>()
    var whiteIdx = 0
    keys.forEach { midi ->
        if (!isBlackKey(midi)) {
            result[midi] = NotePosition(
                left = whiteIdx * whiteKeyWidthFraction,
                width = whiteKeyWidthFraction,
                isBlack = false
            )
            whiteIdx++
        } else {
            result[midi] = NotePosition(
                left = (whiteIdx - 0.3f) * whiteKeyWidthFraction,
                width = whiteKeyWidthFraction * 0.6f,
                isBlack = true
            )
        }
    }
    return result
}
