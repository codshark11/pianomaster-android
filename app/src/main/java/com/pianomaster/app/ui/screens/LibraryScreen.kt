package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.data.Difficulty
import com.pianomaster.app.data.SongsRepository
import com.pianomaster.app.data.Song
import com.pianomaster.app.ui.components.SongCard
import com.pianomaster.app.ui.components.dialogs.AuthDialog
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.DifficultyEasy
import com.pianomaster.app.ui.theme.DifficultyHard
import com.pianomaster.app.ui.theme.DifficultyMedium
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.StreakOrange
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary
import com.pianomaster.app.ui.theme.TrophyYellow
import com.pianomaster.app.ui.theme.XPBarEnd
import com.pianomaster.app.ui.theme.XPBarStart

private data class LeaderboardPlayer(val rank: Int, val name: String, val score: Int, val avatarColor: Color)

@Composable
fun LibraryScreen(
    onNavigateToTrainer: (String) -> Unit,
    onNavigateToStore: () -> Unit = {},
    onNavigateToSettings: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    var level by remember { mutableStateOf(5) }
    var xp by remember { mutableStateOf(1250) }
    var streak by remember { mutableStateOf(5) }
    var lastPlayedSongId by remember { mutableStateOf<String?>("1") }
    var unlockedSongIds by remember { mutableStateOf(listOf("1", "2", "3")) }
    var isAuthOpen by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }

    val songs = SongsRepository.songs
    val lastSong = lastPlayedSongId?.let { id -> songs.find { it.id == id } }
    val topPlayers = remember {
        listOf(
            LeaderboardPlayer(1, "Virtuoso99", 154200, Color(0xFF9333EA)),
            LeaderboardPlayer(2, "PianoMan", 142050, Color(0xFF3B82F6)),
            LeaderboardPlayer(3, "MelodyJane", 138900, Color(0xFFEC4899)),
            LeaderboardPlayer(4, "KeyMaster", 125000, Color(0xFF22C55E)),
            LeaderboardPlayer(5, "BeethovenJr", 110500, Color(0xFFF97316))
        )
    }
    val xpProgress = 0.65f

    AuthDialog(
        open = isAuthOpen,
        onOpenChange = { isAuthOpen = it },
        onLogin = { isLoggedIn = true }
    )
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
        modifier = Modifier.fillMaxSize().background(Background)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            LibraryHud(
                level = level,
                xpProgress = xpProgress,
                xp = xp,
                streak = streak,
                topPlayers = topPlayers
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(modifier = Modifier.padding(bottom = 24.dp)) {
                    if (lastSong != null) {
                        SectionTitle("Recently Played")
                        Spacer(Modifier.height(16.dp))
                        RecentlyPlayedCard(
                            song = lastSong,
                            progress = 0.65f,
                            onClick = {
                                val isUnlocked = unlockedSongIds.contains(lastSong.id)
                                if (lastSong.isLocked && !isUnlocked) {
                                    if (!isLoggedIn) isAuthOpen = true else onNavigateToStore()
                                } else {
                                    lastPlayedSongId = lastSong.id
                                    onNavigateToTrainer(lastSong.id)
                                }
                            }
                        )
                        Spacer(Modifier.height(48.dp))
                    }
                    SectionTitle("Campaign Mode")
                    Spacer(Modifier.height(24.dp))
                }
            }
            items(songs) { song ->
                val isUnlocked = unlockedSongIds.contains(song.id)
                SongCard(
                    song = song,
                    isUnlocked = isUnlocked,
                    onClick = {
                        if (song.isLocked && !isUnlocked) {
                            if (!isLoggedIn) isAuthOpen = true else onNavigateToStore()
                        } else {
                            lastPlayedSongId = song.id
                            onNavigateToTrainer(song.id)
                        }
                    }
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(Modifier.height(120.dp))
            }
        }
}

@Composable
private fun LibraryHud(
    level: Int,
    xpProgress: Float,
    xp: Int,
    streak: Int,
    topPlayers: List<LeaderboardPlayer>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background.copy(alpha = 0.8f))
            .border(1.dp, Color.White.copy(alpha = 0.05f))
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Surface)
                        .border(2.dp, TrophyYellow.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$level", style = MaterialTheme.typography.titleLarge, color = TrophyYellow)
                }
                Column {
                    Text(
                        "Experience",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(128.dp)
                            .height(10.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Surface)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(xpProgress)
                                .height(10.dp)
                                .background(
                                    Brush.horizontalGradient(listOf(XPBarStart, XPBarEnd)),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .background(StreakOrange.copy(alpha = 0.1f), RoundedCornerShape(12.dp))
                    .border(1.dp, StreakOrange.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.Bolt, null, Modifier.size(14.dp), StreakOrange)
                Text("$streak Day Streak", style = MaterialTheme.typography.labelMedium, color = StreakOrange)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.EmojiEvents, null, Modifier.size(14.dp), TrophyYellow)
                Text("Rank #42", style = MaterialTheme.typography.labelMedium, color = TrophyYellow)
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.Star, null, Modifier.size(14.dp), Color(0xFFA78BFA))
                Text("${xp.toString().replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1,")} pts", style = MaterialTheme.typography.labelMedium, color = Color(0xFFC4B5FD))
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Top Players", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Row(
                horizontalArrangement = Arrangement.spacedBy((-8).dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                topPlayers.take(5).forEach { player ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(player.avatarColor)
                            .border(2.dp, Background, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(player.name.take(1), style = MaterialTheme.typography.labelSmall, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.labelSmall,
            color = TextSecondary
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.White.copy(alpha = 0.1f))
        )
    }
}

@Composable
private fun RecentlyPlayedCard(
    song: Song,
    progress: Float,
    onClick: () -> Unit
) {
    val difficultyColor = when (song.difficulty) {
        Difficulty.Easy -> DifficultyEasy
        Difficulty.Medium -> DifficultyMedium
        Difficulty.Hard -> DifficultyHard
    }
    val gradientBrush = Brush.horizontalGradient(
        colors = when (song.difficulty) {
            Difficulty.Easy -> listOf(DifficultyEasy.copy(alpha = 0.2f), Surface)
            Difficulty.Medium -> listOf(DifficultyMedium.copy(alpha = 0.2f), Surface)
            Difficulty.Hard -> listOf(DifficultyHard.copy(alpha = 0.2f), Surface)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Surface)
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(28.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        song.difficulty.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = difficultyColor,
                        modifier = Modifier
                            .background(difficultyColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, null, Modifier.size(14.dp), TrophyYellow)
                        Text("${song.stars ?: 0}/3", style = MaterialTheme.typography.labelSmall, color = TrophyYellow)
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    song.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary
                )
                Text(song.artist, style = MaterialTheme.typography.bodyLarge, color = TextSecondary)
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Surface)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress)
                                .height(8.dp)
                                .background(Primary, RoundedCornerShape(4.dp))
                        )
                    }
                    Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.labelMedium, color = Primary)
                }
            }
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Surface)
                    .border(1.dp, Primary.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.PlayArrow, null, Modifier.size(32.dp), Primary)
            }
        }
    }
}
