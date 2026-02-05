package com.pianomaster.app.data

object SongsRepository {
    private val twinkleNotes = listOf(
        NoteEvent(60, 1.0, 0.45), NoteEvent(60, 1.5, 0.45), NoteEvent(67, 2.0, 0.45),
        NoteEvent(67, 2.5, 0.45), NoteEvent(69, 3.0, 0.45), NoteEvent(69, 3.5, 0.45),
        NoteEvent(67, 4.0, 0.45)
    )
    private val scaleNotes = (60..72).mapIndexed { i, midi ->
        NoteEvent(midi, 1.0 + i * 0.4, 0.36)
    }
    private val odeNotes = listOf(
        NoteEvent(64, 1.0, 0.5), NoteEvent(64, 1.5, 0.5), NoteEvent(65, 2.0, 0.5),
        NoteEvent(67, 2.5, 0.5), NoteEvent(67, 3.0, 0.5), NoteEvent(65, 3.5, 0.5),
        NoteEvent(64, 4.0, 0.5), NoteEvent(62, 4.5, 0.5)
    )

    val songs: List<Song> = listOf(
        Song(
            id = "1", title = "Twinkle Star", artist = "Traditional",
            difficulty = Difficulty.Easy, isLocked = false, bpm = 120, notes = twinkleNotes,
            stars = 3, highScore = 1250, xpReward = 50, totalNotes = twinkleNotes.size,
            userRank = 42, totalPlayers = 15420
        ),
        Song(
            id = "2", title = "C Major Scale", artist = "Practice",
            difficulty = Difficulty.Easy, isLocked = false, bpm = 120, notes = scaleNotes,
            stars = 2, highScore = 850, xpReward = 30, totalNotes = scaleNotes.size,
            userRank = 156, totalPlayers = 8900
        ),
        Song(
            id = "3", title = "Ode to Joy (Intro)", artist = "Beethoven",
            difficulty = Difficulty.Medium, isLocked = false, bpm = 100, notes = odeNotes,
            stars = 0, highScore = 0, xpReward = 100, totalNotes = odeNotes.size,
            totalPlayers = 23500
        ),
        Song(
            id = "4", title = "Moonlight Sonata", artist = "Beethoven",
            difficulty = Difficulty.Hard, isLocked = true, price = 299, bpm = 60, notes = emptyList(),
            xpReward = 200, totalNotes = 450, totalPlayers = 45200
        ),
        Song(
            id = "5", title = "Fur Elise", artist = "Beethoven",
            difficulty = Difficulty.Hard, isLocked = true, price = 399, bpm = 140, notes = emptyList(),
            xpReward = 200, totalNotes = 380, totalPlayers = 67100
        ),
        Song(
            id = "6", title = "Canon in D", artist = "Pachelbel",
            difficulty = Difficulty.Medium, isLocked = true, price = 199, bpm = 80, notes = emptyList(),
            xpReward = 150, totalNotes = 210, totalPlayers = 32400
        )
    )

    fun getSongById(id: String): Song? = songs.find { it.id == id }
}
