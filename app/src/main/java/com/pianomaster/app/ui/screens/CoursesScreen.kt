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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pianomaster.app.data.Course
import com.pianomaster.app.data.CourseLevel
import com.pianomaster.app.data.CoursesRepository
import com.pianomaster.app.ui.theme.Background
import com.pianomaster.app.ui.theme.DifficultyEasy
import com.pianomaster.app.ui.theme.DifficultyHard
import com.pianomaster.app.ui.theme.DifficultyMedium
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun CoursesScreen(
    onSelectCourse: (String) -> Unit,
    onBack: () -> Unit
) {
    val courses = CoursesRepository.courses
    Column(modifier = Modifier.fillMaxSize().background(Background)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background.copy(alpha = 0.8f))
                .border(1.dp, Color.White.copy(alpha = 0.05f))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    "Courses",
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary
                )
                Text(
                    "Master the basics and advance your skills",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(courses) { course ->
                CourseCard(
                    course = course,
                    onClick = { onSelectCourse(course.id) }
                )
            }
            item { Spacer(Modifier.height(100.dp)) }
        }
    }
}

@Composable
private fun CourseCard(
    course: Course,
    onClick: () -> Unit
) {
    val levelColor = when (course.level) {
        CourseLevel.Beginner -> DifficultyEasy
        CourseLevel.Intermediate -> DifficultyMedium
        CourseLevel.Advanced -> DifficultyHard
    }
    val gradientColors = when (course.level) {
        CourseLevel.Beginner -> listOf(Primary.copy(alpha = 0.3f), Color(0xFF06B6D4).copy(alpha = 0.2f))
        CourseLevel.Intermediate -> listOf(Color(0xFFA78BFA).copy(alpha = 0.3f), Color(0xFFEC4899).copy(alpha = 0.2f))
        CourseLevel.Advanced -> listOf(Color(0xFFF97316).copy(alpha = 0.3f), Color(0xFFEF4444).copy(alpha = 0.2f))
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Surface)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Brush.linearGradient(gradientColors))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    course.level.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = levelColor,
                    modifier = Modifier
                        .background(levelColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                course.title,
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary
            )
            Text(
                course.description,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                maxLines = 2
            )
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(Icons.Filled.MenuBook, null, Modifier.size(14.dp), TextSecondary)
                Text(
                    "${course.totalLessons} lessons • ${course.progress}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = TextSecondary
                )
            }
        }
    }
}
