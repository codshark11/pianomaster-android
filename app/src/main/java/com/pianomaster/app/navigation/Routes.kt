package com.pianomaster.app.navigation

sealed class Route(val route: String) {
    data object Library : Route("library")
    data object Courses : Route("courses")
    data object CourseDetail : Route("course/{courseId}") {
        fun withId(id: String) = "course/$id"
    }
    data object Lesson : Route("lesson/{courseId}/{lessonId}") {
        fun withIds(courseId: String, lessonId: String) = "lesson/$courseId/$lessonId"
    }
    data object Trainer : Route("trainer/{songId}") {
        fun withId(songId: String) = "trainer/$songId"
    }
    data object Store : Route("store")
    data object Profile : Route("profile")
    data object Settings : Route("settings")
}
