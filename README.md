# Goblin Mod (NeoForge 1.21.1)

Adds a fully animated Goblin mob.

## Build and run
Requires JDK 21. This project does not ship the Gradle wrapper jar, so do one of:

- Copy `gradlew`, `gradlew.bat` and the `gradle/` folder from the official MDK
  (https://github.com/NeoForgeMDKs/MDK-1.21.1-ModDevGradle), or
- run `gradle wrapper --gradle-version 8.8` if you have Gradle installed.

Then:

    ./gradlew runClient     # test in-game
    ./gradlew build         # jar appears in build/libs/

In game: `/summon goblinmod:goblin`, or use the Goblin Spawn Egg from the Spawn Eggs creative tab.

## What's animated
- **Walk:** fast scurrying leg/arm cycle, bouncing body, forward lean, flapping ears
- **Idle (looping keyframes):** breathing, head tilt, ear twitches, sniffing nose, fidgeting hand
- **Attack (keyframes, triggered by entity event 4):** wind-up, lunge with body twist, dagger thrust, recovery
- **Aggressive stance:** dagger arm raised while it has a target
- Head follows the look target

## Files worth editing
- `client/GoblinAnimations.java`  keyframe timelines
- `client/GoblinModel.java`       geometry and procedural motion
- `textures/entity/goblin.png`    64x64 skin
- `data/.../biome_modifier/goblin_spawns.json`  spawn rules
- `data/.../loot_table/entities/goblin.json`    drops
