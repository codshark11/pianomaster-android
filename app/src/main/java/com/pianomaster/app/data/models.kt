package com.pianomaster.app.data

data class NoteEvent(
    val midi: Int,
    val time: Double,
    val duration: Double
)

enum class Difficulty { Easy, Medium, Hard }

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val cover: String? = null,
    val difficulty: Difficulty,
    val isLocked: Boolean,
    val price: Int? = null,
    val notes: List<NoteEvent>,
    val bpm: Int,
    val stars: Int? = null,
    val highScore: Int? = null,
    val xpReward: Int? = null,
    val totalNotes: Int,
    val userRank: Int? = null,
    val totalPlayers: Int? = null
)

enum class LessonType { VIDEO, ARTICLE, PRACTICE }

data class Lesson(
    val id: String,
    val title: String,
    val duration: String,
    val isCompleted: Boolean,
    val isLocked: Boolean,
    val type: LessonType,
    val description: String? = null,
    val videoUrl: String? = null,
    val content: String? = null,
    val practiceTarget: List<Int>? = null,
    val practiceGoal: String? = null
)

enum class CourseLevel { Beginner, Intermediate, Advanced }

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val level: CourseLevel,
    val image: String,
    val progress: Int,
    val totalLessons: Int,
    val lessons: List<Lesson>
)
