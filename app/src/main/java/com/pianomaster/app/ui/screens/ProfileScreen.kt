package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary
import com.pianomaster.app.ui.theme.TrophyYellow
import com.pianomaster.app.ui.theme.XPBarEnd
import com.pianomaster.app.ui.theme.XPBarStart

@Composable
fun ProfileScreen(onBack: () -> Unit) {
    val level = 5
    val xp = 1250
    val xpProgress = 0.65f

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                "My Profile",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Spacer(Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface, RoundedCornerShape(24.dp))
                    .padding(32.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(112.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Primary, PrimaryVariant))),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "JD",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "John Doe",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary
                    )
                    Text(
                        "Pianist since Jan 2024",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(Primary, PrimaryVariant)),
                        RoundedCornerShape(24.dp)
                    )
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, null, Modifier.size(24.dp), Color.White)
                        Spacer(Modifier.padding(8.dp))
                        Text("Credits", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    }
                    Text("500", style = MaterialTheme.typography.headlineSmall, color = Color.White)
                }
            }
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("$level", style = MaterialTheme.typography.titleLarge, color = TrophyYellow)
                    }
                    Text("Level", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                }
                Column(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
                    Text("Experience", style = MaterialTheme.typography.labelMedium, color = TextSecondary)
                    Spacer(Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Surface)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(xpProgress)
                                .height(8.dp)
                                .background(
                                    Brush.horizontalGradient(listOf(XPBarStart, XPBarEnd)),
                                    RoundedCornerShape(4.dp)
                                )
                        )
                    }
                    Text("$xp pts", style = MaterialTheme.typography.labelSmall, color = TextPrimary)
                }
            }
            Spacer(Modifier.height(48.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Log out",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}
