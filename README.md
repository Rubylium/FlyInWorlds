# FlyInWorlds Minecraft Plugin
This is a Minecraft plugin for my server that enables flying and disables fall damage in specific worlds. I share the source code publicly.

[![Build, Test, and Release](https://github.com/CrimsonWarpedcraft/plugin-template/actions/workflows/main.yml/badge.svg)](https://github.com/CrimsonWarpedcraft/plugin-template/actions/workflows/main.yml)

## Features
### Auto-Enable Fly
Automatically enables flying for players entering worlds matching a specific pattern.

### Disable Fall Damage
Prevents fall damage in those worlds.

## Installation
1. Clone the repository.
2. Update the `build.gradle` and `plugin.yml` with your specific details.
3. Build the plugin using `./gradlew build`.
4. Place the JAR file in your server's `plugins` directory.

## Configuration
Update the regex pattern in `ExamplePlugin.java` to match your target worlds:

```java
private final Pattern worldNamePattern = Pattern.compile("player-.*");
```