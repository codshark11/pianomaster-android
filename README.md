# PianoMaster Android

Native Android app for PianoMaster, matching the web app UI (Kotlin + Jetpack Compose).

## Requirements

- Android Studio Ladybug (2024.2) or newer (or use command line)
- JDK 17
- Android SDK 34, minSdk 26

## Open in Android Studio

1. Open Android Studio.
2. **File → Open** and select the `android` folder (sibling to the web app).
3. Wait for Gradle sync.
4. Run on an emulator or device (e.g. **Run → Run 'app'**).

## Command line

```bash
cd android
./gradlew assembleDebug
# Install: adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## Structure

- `app/src/main/java/com/pianomaster/app/`
  - `ui/theme/` – Theme, colors, typography
  - `ui/screens/` – Library, Trainer, Courses, Store, Profile, Settings, etc.
  - `ui/components/` – SongCard, PianoKeys, NoteCascade, TrainerHeader, dialogs
  - `navigation/` – Routes and NavHost
  - `data/` – Models and repositories (songs, courses)
  - `audio/` – Piano sound playback (sine tone on key press)
  - `utils/` – Music utils (e.g. getNoteFromMidi)

## Features

- **Library**: HUD (level, XP, streak), recently played card, song grid; tap song → Trainer.
- **Trainer**: Header, note cascade or sheet placeholder, interactive piano keys with sound.
- **Courses**: List → course detail → lessons (article / video placeholder / practice with piano).
- **Store**: Credits, locked songs, purchase (credits), add-credits dialog.
- **Profile**: User card, credits, level/XP, logout.
- **Settings**: Input (MIDI / Mic), visual (Cascade / Sheet), connect placeholder.
- **Dialogs**: Auth (sign in), Add credits.

Design follows the web app dark theme (slate-950, blue/indigo accents, rounded cards).
