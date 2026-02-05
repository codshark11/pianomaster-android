package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var inputMode by remember { mutableStateOf("MIDI") }
    var visualMode by remember { mutableStateOf("CASCADE") }
    var isConnected by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().background(Background)) {
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(
                "Settings",
                style = MaterialTheme.typography.headlineLarge,
                color = TextPrimary
            )
            Spacer(Modifier.height(32.dp))
            Text(
                "Input Setup",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (inputMode == "MIDI") PrimaryVariant.copy(alpha = 0.1f) else Surface)
                        .border(
                            2.dp,
                            if (inputMode == "MIDI") PrimaryVariant else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { inputMode = "MIDI" }
                        .padding(20.dp)
                ) {
                    Column {
                        if (inputMode == "MIDI") {
                            Icon(Icons.Filled.Check, null, Modifier.size(20.dp).align(Alignment.End), PrimaryVariant)
                        }
                        Icon(Icons.Filled.Keyboard, null, Modifier.size(32.dp), if (inputMode == "MIDI") PrimaryVariant else TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Text("MIDI Keyboard", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                        Text("USB or Bluetooth", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (inputMode == "MIC") PrimaryVariant.copy(alpha = 0.1f) else Surface)
                        .border(
                            2.dp,
                            if (inputMode == "MIC") PrimaryVariant else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { inputMode = "MIC" }
                        .padding(20.dp)
                ) {
                    Column {
                        if (inputMode == "MIC") {
                            Icon(Icons.Filled.Check, null, Modifier.size(20.dp).align(Alignment.End), PrimaryVariant)
                        }
                        Icon(Icons.Filled.Mic, null, Modifier.size(32.dp), if (inputMode == "MIC") PrimaryVariant else TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Text("Microphone", style = MaterialTheme.typography.titleSmall, color = TextPrimary)
                        Text("Acoustic piano", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Surface)
                    .padding(16.dp)
            ) {
                Text(
                    if (isConnected) "Connected" else "Tap to connect",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isConnected) Color(0xFF10B981) else TextSecondary
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                "Visual Mode",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (visualMode == "CASCADE") PrimaryVariant.copy(alpha = 0.1f) else Surface)
                        .border(
                            2.dp,
                            if (visualMode == "CASCADE") PrimaryVariant else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { visualMode = "CASCADE" }
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Subject, null, Modifier.size(28.dp), if (visualMode == "CASCADE") PrimaryVariant else TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Text("Cascade", style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (visualMode == "SHEET") PrimaryVariant.copy(alpha = 0.1f) else Surface)
                        .border(
                            2.dp,
                            if (visualMode == "SHEET") PrimaryVariant else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { visualMode = "SHEET" }
                        .padding(16.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Subject, null, Modifier.size(28.dp), if (visualMode == "SHEET") PrimaryVariant else TextSecondary)
                        Spacer(Modifier.height(8.dp))
                        Text("Sheet", style = MaterialTheme.typography.labelLarge, color = TextPrimary)
                    }
                }
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}
