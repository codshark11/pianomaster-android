package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreditCard
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.data.SongsRepository
import com.pianomaster.app.ui.components.FloatingBackButton
import com.pianomaster.app.ui.components.SongCard
import com.pianomaster.app.ui.components.dialogs.AddCreditsDialog
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun StoreScreen(onBack: () -> Unit) {
    var credits by remember { mutableStateOf(500) }
    var unlockedSongIds by remember { mutableStateOf(listOf("1", "2", "3")) }
    var isAddCreditsOpen by remember { mutableStateOf(false) }
    val storeSongs = remember { SongsRepository.songs.filter { it.isLocked } }

    AddCreditsDialog(
        open = isAddCreditsOpen,
        onOpenChange = { isAddCreditsOpen = it },
        onAddCredits = { amount -> credits += amount }
    )
    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column(modifier = Modifier.padding(end = 48.dp)) {
                    Text(
                        "Store",
                        style = MaterialTheme.typography.headlineLarge,
                        color = TextPrimary
                    )
                    Text(
                        "Unlock songs with credits",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(listOf(Primary, PrimaryVariant)),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.CreditCard, null, tint = Color.White)
                        Spacer(Modifier.padding(8.dp))
                        Text(
                            "Credits",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Text(
                        "$credits",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { isAddCreditsOpen = true }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add credits", tint = Primary)
                    }
                    Text(
                        "Add Credits",
                        style = MaterialTheme.typography.labelLarge,
                        color = Primary,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
            items(storeSongs) { song ->
                val isUnlocked = unlockedSongIds.contains(song.id)
                SongCard(
                    song = song,
                    isUnlocked = isUnlocked,
                    onClick = {
                        if (isUnlocked) return@SongCard
                        if (credits >= (song.price ?: 0)) {
                            credits -= song.price!!
                            unlockedSongIds = unlockedSongIds + song.id
                        }
                    }
                )
            }
            item { Spacer(Modifier.height(100.dp)) }
        }
        FloatingBackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
    }
}
