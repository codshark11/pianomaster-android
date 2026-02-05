package com.pianomaster.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import com.pianomaster.app.data.Difficulty
import com.pianomaster.app.data.Song
import com.pianomaster.app.ui.theme.CardBackground
import com.pianomaster.app.ui.theme.DifficultyEasy
import com.pianomaster.app.ui.theme.DifficultyHard
import com.pianomaster.app.ui.theme.DifficultyMedium
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.StarPurple
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary
import com.pianomaster.app.ui.theme.TrophyYellow

@Composable
fun SongCard(
    song: Song,
    isUnlocked: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLocked = song.isLocked && !isUnlocked
    val difficultyColor = when (song.difficulty) {
        Difficulty.Easy -> DifficultyEasy
        Difficulty.Medium -> DifficultyMedium
        Difficulty.Hard -> DifficultyHard
    }
    val gradientBrush = Brush.verticalGradient(
        colors = when (song.difficulty) {
            Difficulty.Easy -> listOf(DifficultyEasy.copy(alpha = 0.15f), Surface)
            Difficulty.Medium -> listOf(DifficultyMedium.copy(alpha = 0.15f), Surface)
            Difficulty.Hard -> listOf(DifficultyHard.copy(alpha = 0.15f), Surface)
        }
    )

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(CardBackground)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f / 3f)
                .background(gradientBrush)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(
                    text = song.difficulty.name,
                    color = difficultyColor,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .background(difficultyColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (!isLocked && song.userRank != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Surface.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Filled.EmojiEvents, null, Modifier.size(10.dp), TrophyYellow)
                            Text("#${song.userRank}", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Surface.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 6.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Filled.BarChart, null, Modifier.size(10.dp), Primary)
                        Text("${song.totalNotes}", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                if (isLocked) {
                    Icon(Icons.Filled.Lock, null, Modifier.size(48.dp), TextSecondary)
                } else {
                    Icon(
                        Icons.Filled.PlayArrow,
                        null,
                        Modifier.size(48.dp),
                        Color.White
                    )
                }
            }
            if (!isLocked && song.stars != null) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    repeat(3) { i ->
                        Icon(
                            Icons.Filled.Star,
                            null,
                            Modifier.size(10.dp),
                            if (i < song.stars!!) TrophyYellow else TextSecondary.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        song.title,
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        song.artist,
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (isLocked && song.price != null) {
                    Text(
                        "${song.price}",
                        color = TextPrimary,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier
                            .background(Surface, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
