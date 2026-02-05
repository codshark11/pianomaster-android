package com.pianomaster.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun TrainerHeader(
    title: String,
    difficulty: String,
    isTrial: Boolean,
    progress: Float,
    accuracy: Int,
    visualModeCascade: Boolean,
    onBack: () -> Unit,
    onVisualModeToggle: () -> Unit,
    onSettings: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xE6121214),
                        Color.Transparent
                    )
                )
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
            }
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isTrial) {
                        Text(
                            "TRIAL",
                            style = MaterialTheme.typography.labelSmall,
                            color = PrimaryVariant,
                            modifier = Modifier
                                .background(PrimaryVariant.copy(alpha = 0.2f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        difficulty,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Progress ${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "${accuracy}%",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF10B981)
            )
            Text("Accuracy", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            IconButton(onClick = onVisualModeToggle) {
                Icon(
                    if (visualModeCascade) Icons.Filled.Subject else Icons.Filled.Subject,
                    contentDescription = "Toggle view",
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = TextPrimary)
            }
        }
    }
}
