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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.data.Course
import com.pianomaster.app.data.CourseLevel
import com.pianomaster.app.data.CoursesRepository
import com.pianomaster.app.data.Lesson
import com.pianomaster.app.data.LessonType
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.DifficultyEasy
import com.pianomaster.app.ui.theme.DifficultyHard
import com.pianomaster.app.ui.theme.DifficultyMedium
import com.pianomaster.app.ui.components.FloatingBackButton
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun CourseDetailScreen(
    courseId: String,
    onBack: () -> Unit,
    onSelectLesson: (String) -> Unit
) {
    val course = remember(courseId) { CoursesRepository.getCourseById(courseId) }
    if (course == null) {
        Box(Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
            Text("Course not found", color = TextSecondary)
        }
        return
    }
    CourseDetailContent(
        course = course,
        onBack = onBack,
        onSelectLesson = { lesson -> if (!lesson.isLocked) onSelectLesson(lesson.id) }
    )
}

@Composable
private fun CourseDetailContent(
    course: Course,
    onBack: () -> Unit,
    onSelectLesson: (Lesson) -> Unit
) {
    val levelColor = when (course.level) {
        CourseLevel.Beginner -> DifficultyEasy
        CourseLevel.Intermediate -> DifficultyMedium
        CourseLevel.Advanced -> DifficultyHard
    }
    val gradientColors = when (course.level) {
        CourseLevel.Beginner -> listOf(Color(0xFF3B82F6).copy(alpha = 0.4f), Background)
        CourseLevel.Intermediate -> listOf(Color(0xFFA78BFA).copy(alpha = 0.4f), Background)
        CourseLevel.Advanced -> listOf(Color(0xFFF97316).copy(alpha = 0.4f), Background)
    }

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Brush.verticalGradient(gradientColors))
            )
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(32.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Brush.linearGradient(listOf(levelColor, levelColor.copy(alpha = 0.7f)))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.MenuBook, null, Modifier.size(48.dp), Color.White)
                }
                Column {
                    Text(
                        course.level.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = levelColor,
                        modifier = Modifier
                            .background(levelColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        course.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextPrimary
                    )
                    Text(
                        course.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Surface)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(course.progress / 100f)
                                    .height(10.dp)
                                    .background(Color.White, RoundedCornerShape(4.dp))
                            )
                        }
                        Spacer(Modifier.size(8.dp))
                        Text(
                            "${course.progress}% Complete",
                            style = MaterialTheme.typography.labelMedium,
                            color = TextPrimary
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "Course Content",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                "${course.lessons.size} Lessons",
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(Modifier.height(24.dp))
            course.lessons.forEach { lesson ->
                LessonRow(
                    lesson = lesson,
                    onClick = { onSelectLesson(lesson) }
                )
                Spacer(Modifier.height(12.dp))
            }
            Spacer(Modifier.height(100.dp))
        }
        }
        FloatingBackButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        )
    }
}

@Composable
private fun LessonRow(
    lesson: Lesson,
    onClick: () -> Unit
) {
    val (icon, iconColor) = when {
        lesson.isCompleted -> Icons.Filled.CheckCircle to DifficultyEasy
        lesson.isLocked -> Icons.Filled.Lock to TextSecondary
        else -> Icons.Filled.PlayArrow to TextPrimary
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(if (lesson.isLocked) Surface.copy(alpha = 0.4f) else Surface.copy(alpha = 0.8f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
            .clickable(enabled = !lesson.isLocked, onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (lesson.isCompleted) DifficultyEasy.copy(alpha = 0.2f)
                    else if (lesson.isLocked) Surface
                    else Surface
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, Modifier.size(24.dp), iconColor)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                lesson.title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary
            )
            Text(
                "${lesson.duration} • ${lesson.type.name}",
                style = MaterialTheme.typography.labelSmall,
                color = TextSecondary
            )
        }
        if (!lesson.isLocked) {
            Icon(Icons.Filled.KeyboardArrowRight, null, Modifier.size(20.dp), tint = TextSecondary)
        }
    }
}
