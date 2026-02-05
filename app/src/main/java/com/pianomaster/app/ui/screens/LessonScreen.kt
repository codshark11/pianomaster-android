package com.pianomaster.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pianomaster.app.audio.PianoSound
import com.pianomaster.app.data.CoursesRepository
import com.pianomaster.app.data.LessonType
import com.pianomaster.app.ui.components.PianoKeys
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun LessonScreen(
    courseId: String,
    lessonId: String,
    onBack: () -> Unit
) {
    val course = remember(courseId) { CoursesRepository.getCourseById(courseId) }
    val lesson = remember(courseId, lessonId) {
        course?.lessons?.find { it.id == lessonId }
    }
    if (course == null || lesson == null) {
        Box(Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
            Text("Lesson not found", color = TextSecondary)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
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
                lesson.title,
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary
            )
            Text(
                "${lesson.duration} • ${lesson.type.name}",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
            lesson.description?.let { desc ->
                Spacer(Modifier.height(16.dp))
                Text(
                    desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            Spacer(Modifier.height(24.dp))
            when (lesson.type) {
                LessonType.ARTICLE -> {
                    lesson.content?.let { content ->
                        Text(
                            content.replace("###", "").replace("**", "").trim(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary
                        )
                    }
                }
                LessonType.VIDEO -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .background(Surface),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .padding(32.dp)
                                .background(TextPrimary.copy(alpha = 0.1f), CircleShape)
                        ) {
                            Icon(Icons.Filled.PlayArrow, contentDescription = "Play", tint = TextPrimary)
                        }
                    }
                }
                LessonType.PRACTICE -> {
                    lesson.practiceGoal?.let { goal ->
                        Text(
                            goal,
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Surface)
                    ) {
                        PianoKeys(
                            activeNotes = emptyList(),
                            onNoteDown = { midi -> PianoSound.playNote(midi) },
                            onNoteUp = { },
                            startMidi = 48,
                            endMidi = 72
                        )
                    }
                }
            }
            Spacer(Modifier.height(100.dp))
        }
    }
}
