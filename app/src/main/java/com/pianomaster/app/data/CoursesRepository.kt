package com.pianomaster.app.data

object CoursesRepository {
    private val pianoBasicsLessons: List<Lesson> = listOf(
        Lesson(
            id = "b1-1", title = "Sitting Posture & Hand Shape", duration = "3 min",
            isCompleted = true, isLocked = false, type = LessonType.ARTICLE,
            description = "Correct posture prevents injury and improves your playing technique.",
            content = """
                ### The Importance of Posture

                Sitting correctly at the piano is the foundation of good technique.

                **Key Points:**
                1. **Bench Height:** Your elbows should be slightly higher than the keys.
                2. **Distance:** Sit far enough back so that your knees are slightly under the keyboard.
                3. **Feet:** Keep both feet flat on the floor for stability.
                4. **Hand Shape:** Imagine holding an orange. Your fingers should be curved.
            """.trimIndent()
        ),
        Lesson(
            id = "b1-2", title = "Finding Middle C", duration = "5 min",
            isCompleted = false, isLocked = false, type = LessonType.PRACTICE,
            description = "Locate the most important key on the piano.",
            practiceGoal = "Press Middle C (C4) on your keyboard.",
            practiceTarget = listOf(60)
        ),
        Lesson(
            id = "b1-3", title = "White Keys: A to G", duration = "8 min",
            isCompleted = false, isLocked = true, type = LessonType.VIDEO,
            description = "Understanding the musical alphabet on the keyboard.",
            videoUrl = "https://www.youtube.com/embed/v5r3qB1eEwI"
        ),
        Lesson(
            id = "b1-4", title = "Black Keys: Sharps & Flats", duration = "10 min",
            isCompleted = false, isLocked = true, type = LessonType.PRACTICE,
            description = "Identifying the groups of 2 and 3 black keys.",
            practiceGoal = "Press the group of 2 black keys (C# and D#).",
            practiceTarget = listOf(61, 63)
        ),
        Lesson(
            id = "b1-5", title = "Finger Numbers", duration = "4 min",
            isCompleted = false, isLocked = true, type = LessonType.ARTICLE,
            description = "Standard finger numbering for piano sheet music.",
            content = """
                ### Numbering Your Fingers

                **The System:**
                * **1:** Thumb  * **2:** Index  * **3:** Middle  * **4:** Ring  * **5:** Pinky
                This applies to both hands.
            """.trimIndent()
        )
    )
    private val sheetReadingLessons = listOf(
        Lesson("sr-1", "The Staff & Measure Lines", "5 min", false, false, LessonType.ARTICLE, "Learn about the 5 lines and 4 spaces."),
        Lesson("sr-2", "Treble Clef: G Clef", "7 min", false, true, LessonType.VIDEO, "The treble clef identifies the G above middle C."),
        Lesson("sr-3", "Bass Clef: F Clef", "7 min", false, true, LessonType.VIDEO, "The bass clef identifies the F below middle C."),
        Lesson("sr-4", "Note Values", "10 min", false, true, LessonType.PRACTICE, "Understanding whole, half, and quarter notes.", practiceGoal = "Play a C major chord (C-E-G).", practiceTarget = listOf(60, 64, 67))
    )
    private val chordsLessons = listOf(
        Lesson("ch-1", "Major Triads", "8 min", false, true, LessonType.PRACTICE, "Constructing a major triad.", practiceGoal = "Play a C Major Triad (C-E-G).", practiceTarget = listOf(60, 64, 67)),
        Lesson("ch-2", "Minor Triads", "8 min", false, true, LessonType.PRACTICE, "Constructing a minor triad.", practiceGoal = "Play a C Minor Triad (C-Eb-G).", practiceTarget = listOf(60, 63, 67)),
        Lesson("ch-3", "Inversions", "12 min", false, true, LessonType.VIDEO, "Learn how to rearrange chord notes.")
    )

    val courses: List<Course> = listOf(
        Course(
            id = "basics-101", title = "Piano Basics",
            description = "Learn the geography of the keyboard and hand positioning.",
            level = CourseLevel.Beginner, image = "from-blue-500 to-cyan-400",
            progress = 15, totalLessons = 5, lessons = pianoBasicsLessons
        ),
        Course(
            id = "sheet-reading", title = "Reading Sheet Music",
            description = "Understand the staff, treble clef, and bass clef notes.",
            level = CourseLevel.Beginner, image = "from-purple-500 to-pink-500",
            progress = 0, totalLessons = 8, lessons = sheetReadingLessons
        ),
        Course(
            id = "chords-intro", title = "Introduction to Chords",
            description = "Play your first triads and understand harmony.",
            level = CourseLevel.Intermediate, image = "from-orange-500 to-red-500",
            progress = 0, totalLessons = 6, lessons = chordsLessons
        )
    )

    fun getCourseById(id: String): Course? = courses.find { it.id == id }
}
