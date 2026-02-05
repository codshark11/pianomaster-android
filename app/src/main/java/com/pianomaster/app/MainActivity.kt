package com.pianomaster.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pianomaster.app.ui.theme.PianoMasterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PianoMasterTheme {
                // Will be replaced by NavHost in next step
                com.pianomaster.app.ui.PianoMasterApp()
            }
        }
    }
}
