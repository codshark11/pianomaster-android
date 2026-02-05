package com.pianomaster.app.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pianomaster.app.ui.screens.CourseDetailScreen
import com.pianomaster.app.ui.screens.CoursesScreen
import com.pianomaster.app.ui.screens.LessonScreen
import com.pianomaster.app.ui.screens.LibraryScreen
import com.pianomaster.app.ui.screens.ProfileScreen
import com.pianomaster.app.ui.screens.SettingsScreen
import com.pianomaster.app.ui.screens.StoreScreen
import com.pianomaster.app.ui.screens.TrainerScreen

@Composable
fun PianoMasterNavGraph(
    navController: NavHostController,
    startDestination: String = Route.Library.route,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(200)) },
        exitTransition = { fadeOut(animationSpec = tween(200)) },
        popEnterTransition = { fadeIn(animationSpec = tween(200)) },
        popExitTransition = { fadeOut(animationSpec = tween(200)) }
    ) {
        composable(Route.Library.route) {
            LibraryScreen(
                onNavigateToTrainer = { songId -> navController.navigate(Route.Trainer.withId(songId)) },
                onNavigateToStore = { navController.navigate(Route.Store.route) },
                onNavigateToSettings = { navController.navigate(Route.Settings.route) },
                onNavigateToProfile = { navController.navigate(Route.Profile.route) }
            )
        }
        composable(Route.Courses.route) {
            CoursesScreen(
                onSelectCourse = { courseId -> navController.navigate("course/$courseId") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Route.CourseDetail.route,
            arguments = listOf(navArgument("courseId") { defaultValue = "" })
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            CourseDetailScreen(
                courseId = courseId,
                onBack = { navController.popBackStack() },
                onSelectLesson = { lessonId ->
                    navController.navigate(Route.Lesson.withIds(courseId, lessonId))
                }
            )
        }
        composable(
            route = Route.Lesson.route,
            arguments = listOf(
                navArgument("courseId") { defaultValue = "" },
                navArgument("lessonId") { defaultValue = "" }
            )
        ) { backStackEntry ->
            val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
            val lessonId = backStackEntry.arguments?.getString("lessonId") ?: ""
            LessonScreen(
                courseId = courseId,
                lessonId = lessonId,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Route.Trainer.route,
            arguments = listOf(navArgument("songId") { defaultValue = "" })
        ) { backStackEntry ->
            val songId = backStackEntry.arguments?.getString("songId") ?: ""
            TrainerScreen(
                songId = songId,
                onBack = { navController.popBackStack() },
                onSettings = { navController.navigate(Route.Settings.route) }
            )
        }
        composable(Route.Store.route) {
            StoreScreen(onBack = { navController.popBackStack() })
        }
        composable(Route.Profile.route) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }
        composable(Route.Settings.route) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
